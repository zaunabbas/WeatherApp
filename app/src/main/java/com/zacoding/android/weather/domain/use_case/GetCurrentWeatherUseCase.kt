package com.zacoding.android.weather.domain.use_case

import android.content.Context
import com.zacoding.android.weather.R
import com.zacoding.android.weather.data.DataResource
import com.zacoding.android.weather.data.model.CurrentWeather
import com.zacoding.android.weather.domain.asFlow
import com.zacoding.android.weather.domain.exception.BaseException
import com.zacoding.android.weather.domain.repository.WeatherRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    @ApplicationContext private val context: Context
) : FlowUseCase<GetCurrentWeatherUseCase.Params, DataResource<CurrentWeather>>(dispatcher) {

    override fun execute(params: Params?): Flow<DataResource<CurrentWeather>> {

        if (params?.city?.isNotEmpty() == true) {
            return weatherRepository.getCurrentWeatherByCity(params.city)
        }

        return BaseException.AlertException(-1, context.getString(R.string.city_input_invalid))
            .asFlow()
    }

    data class Params(val city: String)
}