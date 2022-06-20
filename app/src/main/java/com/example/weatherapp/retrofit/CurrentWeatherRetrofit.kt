package com.example.weatherapp.retrofit


import com.example.weatherapp.api.models.CurrentWeatherApiResponse
import retrofit2.Response
import retrofit2.http.*

interface CurrentWeatherRetrofit {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("units") units: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") key: String
    ): Response<CurrentWeatherApiResponse>
}
