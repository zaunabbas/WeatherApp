package com.zacoding.android.weather.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("cod") val cod: Int? = null,
    @SerializedName("weather") val weatherItems: List<WeatherItem>? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("main") val main: Main? = null,
    @SerializedName("visibility") val visibility: Int? = null,
    @SerializedName("wind") val wind: Wind? = null,
    @SerializedName("clouds") val clouds: Cloud? = null,
    @SerializedName("dt") val dt: Long? = null,
    @SerializedName("sys") val sys: Sys? = null,
    @SerializedName("timezone") val timezone: Int? = null
)