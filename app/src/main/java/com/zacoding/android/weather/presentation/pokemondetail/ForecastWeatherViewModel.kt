package com.zacoding.android.weather.presentation.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.domain.use_case.GetForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    private val pokemonInfoUseCase: GetForecastWeatherUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DataResource<CurrentWeather>> =
        MutableStateFlow(DataResource.Empty)
    var state: StateFlow<DataResource<CurrentWeather>> = _state

    fun getPokemonInfo(pokemonName: String) {

        viewModelScope.launch {
            pokemonInfoUseCase.invoke(pokemonName).collectLatest {
                _state.value = it
            }
        }
    }
}