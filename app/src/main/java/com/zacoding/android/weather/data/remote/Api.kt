package com.zacoding.android.weather.data.remote

import com.zacoding.android.weather.data.Constants.OpenWeather.APP_ID
import com.zacoding.android.weather.data.Constants.OpenWeather.WEATHER_UNITS
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.data.remote.responses.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("units") units: String = WEATHER_UNITS,
        @Query("appid") appId: String = APP_ID,
    ): CurrentWeather

    @GET("forecast")
    suspend fun getHourlyWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("units") units: String = WEATHER_UNITS,
        @Query("appid") appId: String = APP_ID,
    ): ForecastResponse
}
