package com.example.weatherapp.api.models

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val countryCode: String,
    @SerializedName("sunrise")
    val sunriseTime: Int,
    @SerializedName("sunset")
    val sunsetTime: Int
)