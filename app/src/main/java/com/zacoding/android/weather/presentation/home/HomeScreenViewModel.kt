package com.zacoding.android.weather.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.PokemonListEntry
import com.zacoding.android.weather.data.model.Query
import com.zacoding.android.weather.data.onError
import com.zacoding.android.weather.data.onSuccess
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.domain.use_case.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {


    private val _currentWeatherState: MutableStateFlow<DataResource<CurrentWeather>> =
        MutableStateFlow(DataResource.Empty)
    var currentWeatherState: StateFlow<DataResource<CurrentWeather>> = _currentWeatherState
    var currentWeather = mutableStateOf(CurrentWeather())

    private var currentPage = 0
    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var loadError = mutableStateOf("Search for a city")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokemonListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        getCurrentWeather("Karachi")
    }

    fun getCurrentWeather(city: String) {
        viewModelScope.launch {
            isLoading.value = true

            val query = Query(city = city)
            getCurrentWeatherUseCase.invoke(query).collectLatest { result ->

                result.onSuccess {
                    loadError.value = ""
                    isLoading.value = false
                    currentWeather.value = this.data

                }.onError {
                    loadError.value = this.exception.message!!
                    isLoading.value = false
                }
            }

        }
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }

            val result = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }

            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }

            pokemonList.value = result
            isSearching.value = true
        }

    }

}