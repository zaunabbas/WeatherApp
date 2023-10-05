package com.zacoding.android.weather.presentation.model

import android.content.Context
import com.zacoding.android.weather.R
import com.zacoding.android.weather.data.Constants
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.domain.toDateTimeString
import com.zacoding.android.weather.presentation.base.ModelMapper
import com.zacoding.android.weather.presentation.base.ViewDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.round

data class HourlyWeatherViewDataModel(
    val dt: Long,
    val hour: String,
    val temp: String,
    val humidity: String,
    val wind: String,
    val visibility: String,
    val realFeel: String,
    val icon: String
) : ViewDataModel()

class HourlyWeatherMapper @Inject constructor(
    @ApplicationContext private val context: Context
) : ModelMapper<CurrentWeather, HourlyWeatherViewDataModel> {

    override fun mapperToViewDataModel(dataModel: CurrentWeather): HourlyWeatherViewDataModel {
        return HourlyWeatherViewDataModel(
            dt = dataModel.dt,
            hour = (dataModel.dt * 1000L).toDateTimeString(Constants.DateFormat.HH_mm, zone = null),
            temp = "${round(dataModel.main.temp).toInt()}",
            humidity = "${dataModel.main.humidity}${context.getString(R.string.percent)}",
            wind = "${round(dataModel.wind.speed).toInt()} ${context.getString(R.string.speed)}",
            visibility = "${dataModel.visibility / 1000} ${context.getString(R.string.km)}",
            realFeel = "${round(dataModel.main.feelsLike).toInt()}${context.getString(R.string.temp)}",
            icon = String.format(
                Constants.OpenWeather.WEATHER_SMALL_ICON_URL,
                dataModel.weatherItems.first().icon
            )
        )
    }
}
