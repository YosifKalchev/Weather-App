package com.example.weatherapp.api.models

import com.google.gson.annotations.SerializedName

data class Main(
    val humidity: Int,
    val pressure: Int,

    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("temp_max")
    val maxTemperature: Double,
    @SerializedName("temp_min")
    val minTemperature: Double
)