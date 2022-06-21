package com.example.weatherapp.repo

import com.example.weatherapp.api.models.CurrentWeatherApiResponse
import com.example.weatherapp.retrofit.CurrentWeatherRetrofit
import retrofit2.Response

class CurrentWeatherRepo
constructor(
    private val currentWeatherRetrofit: CurrentWeatherRetrofit,
) {
    suspend fun getCurrentWeather(
        units: String, latitude: String, longitude: String, key: String
    ): Response<CurrentWeatherApiResponse> =
        currentWeatherRetrofit.getCurrentWeather(units, latitude, longitude, key)
}