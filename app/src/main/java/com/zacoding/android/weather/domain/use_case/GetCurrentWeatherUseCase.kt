package com.zacoding.android.weather.domain.use_case

import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.Query
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.domain.repository.WeatherRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Query, DataResource<CurrentWeather>>(dispatcher) {

    override fun execute(query: Query): Flow<DataResource<CurrentWeather>> {
        return weatherRepository.getCurrentWeatherByCity(query.city)
    }
}