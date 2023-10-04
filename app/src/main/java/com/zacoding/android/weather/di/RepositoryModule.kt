package com.zacoding.android.weather.di

import com.zacoding.android.weather.data.remote.Api
import com.zacoding.android.weather.data.repository.WeatherRepositoryImpl
import com.zacoding.android.weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun providePokemonRepository(
    service: Api
  ): WeatherRepository {
    return WeatherRepositoryImpl(service)
  }
}
