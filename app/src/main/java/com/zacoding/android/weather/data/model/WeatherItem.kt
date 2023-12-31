package com.zacoding.android.weather.data.model

import com.zacoding.android.weather.data.base.DataModel
import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
) : DataModel()
