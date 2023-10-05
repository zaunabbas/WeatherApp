package com.zacoding.android.weather.domain.repository

import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.data.remote.responses.ForecastResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCurrentWeatherByCity(city: String): Flow<DataResource<CurrentWeather>>
    fun getHourlyWeather(lat: Double, lon: Double): Flow<DataResource<ForecastResponse>>
}