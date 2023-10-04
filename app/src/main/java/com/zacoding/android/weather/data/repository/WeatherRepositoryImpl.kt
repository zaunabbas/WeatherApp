package com.zacoding.android.weather.data.repository

import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.callApi
import com.zacoding.android.weather.data.remote.Api
import com.zacoding.android.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: Api
) : WeatherRepository {

    override fun getCurrentWeatherByCity(city: String) = flow {
        emit(DataResource.Loading)
        val result = callApi {
            apiService.getCurrentWeatherByCity(city)
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getWeatherForecast(city: String) = flow {
        emit(DataResource.Loading)
        val result = callApi {
            apiService.getFiveDaysWeather(city)
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

}