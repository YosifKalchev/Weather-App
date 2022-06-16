package com.example.weatherapp.di.module

import com.example.weatherapp.repo.CurrentWeatherRepo
import com.example.weatherapp.retrofit.CurrentWeatherRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrentWeatherRepoModule {

    @Singleton
    @Provides
    fun provideCurrentWeatherRepo(
        placesRetrofit: CurrentWeatherRetrofit
    ): CurrentWeatherRepo {
        return CurrentWeatherRepo(
            placesRetrofit
        )
    }
}