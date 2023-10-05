package com.zacoding.android.weather.data.model

import com.zacoding.android.weather.data.base.DataModel
import com.google.gson.annotations.SerializedName

data class FeelLike(
    @SerializedName("day") val day: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val eve: Double,
    @SerializedName("morn") val morn: Double
) : DataModel()
