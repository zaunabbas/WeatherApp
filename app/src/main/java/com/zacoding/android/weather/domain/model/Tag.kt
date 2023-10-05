package com.zacoding.android.weather.domain.model

import com.example.weather.domain.annotation.TagName

data class Tag(@TagName val name: String, val message: String?)
