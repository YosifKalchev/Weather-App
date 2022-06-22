package com.example.weatherapp.di.module

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.retrofit.CurrentWeatherRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitCurrentWeatherModule {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttp =
        OkHttpClient.Builder().addInterceptor(logger)
            .addInterceptor(
                CurlInterceptor(object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                })
            )

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CURRENT_WEATHER_BASE_URL) //"https://api.openweathermap.org/"
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp.build())
    }

    @Singleton
    @Provides
    fun provideMyplacesService(retrofit: Retrofit.Builder): CurrentWeatherRetrofit {
        return retrofit
            .build()
            .create(CurrentWeatherRetrofit::class.java)
    }
}