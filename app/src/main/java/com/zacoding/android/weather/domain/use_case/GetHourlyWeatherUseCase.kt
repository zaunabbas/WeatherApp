package com.zacoding.android.weather.domain.use_case

import android.content.Context
import com.zacoding.android.weather.R
import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.data
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.data.model.Hourly
import com.zacoding.android.weather.domain.asFlow
import com.zacoding.android.weather.domain.exception.BaseException
import com.zacoding.android.weather.domain.repository.WeatherRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

@ActivityScoped
class GetHourlyWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    @ApplicationContext private val context: Context
) : FlowUseCase<GetHourlyWeatherUseCase.Params, GetHourlyWeatherUseCase.Response>(dispatcher) {

    override fun execute(parameters: Params?): Flow<Response> {

        if (parameters != null) {
            return weatherRepository.getHourlyWeather(parameters.lat, parameters.long)
                .map { it.data?.hourly ?: emptyList() }
                .map { hourly ->
                    Response(
                        today = hourly.filter { it.dt <= maxToday() }
                    )
                }
        }

        return BaseException.AlertException(-1, context.getString(R.string.lat_lon_invalid))
            .asFlow()
    }

    private fun maxToday(): Long {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.add(Calendar.DATE, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 6)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.timeInMillis / 1000L
    }

    data class Params(val lat: Double, val long: Double)

    data class Response(val today: List<CurrentWeather>)

}