package com.zacoding.android.weather.domain.use_case

import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.domain.repository.WeatherRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class GetForecastWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<String, DataResource<CurrentWeather>>(dispatcher) {

    override fun execute(pokemonName: String): Flow<DataResource<CurrentWeather>> {
        return weatherRepository.getWeatherForecast(pokemonName)
    }
}