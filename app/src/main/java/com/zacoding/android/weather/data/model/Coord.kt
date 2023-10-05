package com.zacoding.android.weather.data.model

import com.zacoding.android.weather.data.base.DataModel
import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val long: Double
) : DataModel()
