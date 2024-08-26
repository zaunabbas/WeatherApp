package com.zacoding.android.weather.domain.model
import com.zacoding.android.weather.domain.annotation.Redirect

data class Redirect(@Redirect val redirect: Int, val redirectObject: Any? = null)
