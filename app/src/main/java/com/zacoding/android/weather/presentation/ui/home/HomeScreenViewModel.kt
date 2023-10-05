package com.zacoding.android.weather.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.zacoding.android.weather.data.onError
import com.zacoding.android.weather.data.onSuccess
import com.zacoding.android.weather.domain.exception.BaseException
import com.zacoding.android.weather.domain.use_case.GetCurrentWeatherUseCase
import com.zacoding.android.weather.domain.use_case.GetHourlyWeatherUseCase
import com.zacoding.android.weather.presentation.base.BaseViewModel
import com.zacoding.android.weather.presentation.base.ViewState
import com.zacoding.android.weather.presentation.base.toBaseException
import com.zacoding.android.weather.presentation.model.CurrentWeatherMapper
import com.zacoding.android.weather.presentation.model.CurrentWeatherViewDataModel
import com.zacoding.android.weather.presentation.model.HourlyWeatherMapper
import com.zacoding.android.weather.presentation.model.HourlyWeatherViewDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface WeatherState {
    val weathers: List<HourlyWeatherViewDataModel>

    data class Today(
        override val weathers: List<HourlyWeatherViewDataModel> = emptyList(),
    ) : WeatherState
}

sealed interface SearchState {
    val enabled: Boolean
    val query: String

    data class Changing(
        override val enabled: Boolean = true,
        override val query: String = ""
    ) : SearchState

    data class Closed(
        override val enabled: Boolean = false,
        override val query: String = ""
    ) : SearchState
}

data class HomeViewState(
    override val isLoading: Boolean = false,
    override val exception: BaseException? = null,
    val currentWeather: CurrentWeatherViewDataModel? = null,
    val weatherState: WeatherState = WeatherState.Today(),
    val searchState: SearchState = SearchState.Closed()
) : ViewState(isLoading, exception)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val weatherMapper: CurrentWeatherMapper,
    private val getHourlyWeatherUseCase: GetHourlyWeatherUseCase,
    private val hourlyWeatherMapper: HourlyWeatherMapper
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeViewState(isLoading = true))
    override val state: StateFlow<HomeViewState>
        get() = _state

    private val coordinate = MutableStateFlow(Pair(0.0, 0.0))

    private val todayState = MutableStateFlow(WeatherState.Today())

    init {
        getWeather("Karachi")
    }

    fun getWeather(city: String) {
        _state.update { HomeViewState(isLoading = true) }

        viewModelScope.launch {

            getCurrentWeatherUseCase.invoke(
                GetCurrentWeatherUseCase.Params(city = city)
            ).collect { result ->

                result.onSuccess {
                    coordinate.update {
                        it.copy(
                            first = data.coord.lat,
                            second = data.coord.long
                        )
                    }
                    getHourlyWeathers(data.coord.lat, data.coord.long)
                    weatherMapper.mapperToViewDataModel(data)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            currentWeather = weatherMapper.mapperToViewDataModel(data)
                        )
                    }

                }.onError {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            exception = this.exception.toBaseException()
                        )
                    }
                }
            }

        }
    }

    /**
     * Notify that the user when typing the search input
     */
    fun onSearchInputChanged(searchInput: String) {
        _state.update {
            it.copy(searchState = SearchState.Changing(query = searchInput))
        }
    }

    /**
     * Enable or disable search view
     */
    fun enableSearchView(enabled: Boolean) {
        _state.update { state ->
            state.copy(
                searchState = if (enabled) SearchState.Changing() else SearchState.Closed(
                    query = state.searchState.query
                )
            )
        }
    }

    private fun getHourlyWeathers(lat: Double, long: Double) {
        viewModelScope.launch {
            getHourlyWeatherUseCase.invoke(GetHourlyWeatherUseCase.Params(lat, long))
                .catch { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            exception = throwable.toBaseException()
                        )
                    }
                }
                .map { response ->
                    response.today.map { hourlyWeatherMapper.mapperToViewDataModel(it) }
                }
                .collect { todayList ->
                    todayState.update { WeatherState.Today(weathers = todayList) }
                    _state.update { it.copy(weatherState = todayState.value) }
                }
        }
    }

}