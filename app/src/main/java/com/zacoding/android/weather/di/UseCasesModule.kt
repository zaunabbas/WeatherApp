package com.zacoding.android.weather.di

import android.content.Context
import com.zacoding.android.weather.domain.repository.WeatherRepository
import com.zacoding.android.weather.domain.use_case.GetCurrentWeatherUseCase
import com.zacoding.android.weather.domain.use_case.GetHourlyWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideGetCurrentWeatherUseCase(
        weatherRepository: WeatherRepository,
        @ApplicationContext context: Context
    ): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(weatherRepository, context = context)
    }

    @Provides
    @ViewModelScoped
    fun provideGetHourlyWeatherUseCase(
        weatherRepository: WeatherRepository,
        @ApplicationContext context: Context
    ): GetHourlyWeatherUseCase {
        return GetHourlyWeatherUseCase(weatherRepository, context = context)
    }
}
