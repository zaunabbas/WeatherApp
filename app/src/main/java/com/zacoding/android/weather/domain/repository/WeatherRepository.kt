package com.zacoding.android.weather.domain.repository

import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCurrentWeatherByCity(city: String): Flow<DataResource<CurrentWeather>>
    fun getWeatherForecast(city: String): Flow<DataResource<CurrentWeather>>
}