package com.zacoding.android.weather.data.model

import com.google.gson.annotations.SerializedName
import com.zacoding.android.weather.data.base.DataModel

data class Cloud(
    @SerializedName("all") val all: Int
) : DataModel()
