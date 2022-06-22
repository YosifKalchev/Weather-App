package com.example.weatherapp.api.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("description")
    val weatherDescription: String,
    @SerializedName("icon")
    val weatherIcon: String
)