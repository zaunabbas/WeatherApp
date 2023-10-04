package com.zacoding.android.weather.data.remote

import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.util.Constants.APP_ID
import com.zacoding.android.weather.util.Constants.WEATHER_UNITS
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
    suspend fun getFiveDaysWeather(
        @Query("q") city: String,
        @Query("units") units: String = WEATHER_UNITS,
        @Query("appid") appId: String = APP_ID,
    ): CurrentWeather
}
