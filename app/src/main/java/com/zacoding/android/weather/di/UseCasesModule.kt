package com.zacoding.android.weather.di

import com.zacoding.android.weather.domain.repository.WeatherRepository
import com.zacoding.android.weather.domain.use_case.GetCurrentWeatherUseCase
import com.zacoding.android.weather.domain.use_case.GetForecastWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideFetchAllPokemonsUseCase(
        weatherRepository: WeatherRepository
    ): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(weatherRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPokemonIngoUseCase(
        weatherRepository: WeatherRepository
    ): GetForecastWeatherUseCase {
        return GetForecastWeatherUseCase(weatherRepository)
    }
}
