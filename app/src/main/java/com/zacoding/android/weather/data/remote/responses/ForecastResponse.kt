package com.zacoding.android.weather.data.remote.responses

import com.google.gson.annotations.SerializedName
import com.zacoding.android.weather.data.model.Current
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.data.model.Daily
import com.zacoding.android.weather.data.model.Hourly

data class ForecastResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezoneOffset: Int,
    @SerializedName("current") val current: Current,
    @SerializedName("list") val hourly: List<CurrentWeather>?,
    @SerializedName("daily") val daily: List<Daily>?
)
