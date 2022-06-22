package com.example.weatherapp.api.models

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val windSpeedInMs: Double
)