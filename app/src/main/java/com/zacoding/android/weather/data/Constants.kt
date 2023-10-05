package com.zacoding.android.weather.data

import com.zacoding.android.weather.BuildConfig

object Constants {
    object DateFormat {
        const val EEEE_dd_MMMM = "EEEE',' dd MMMM"
        const val DEFAULT_FORMAT = "dd-mm-yyyy"
        const val HH_mm = "HH:mm"
    }
    object OpenWeather {
        const val WEATHER_ICON_URL = "https://openweathermap.org/img/wn/%s@4x.png"
        const val WEATHER_SMALL_ICON_URL = "https://openweathermap.org/img/wn/%s.png"
        const val WEATHER_UNITS = "metric"
        const val APP_ID = BuildConfig.OPEN_WEATHER_APP_KEY
    }
}