package com.example.weatherapp.api.models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherApiResponse(

    val wind: Wind,
    @SerializedName("dt")
    val weatherRequestTime: Int,
    @SerializedName("main")
    val mainInfo: Main,
    @SerializedName("name")
    val locationName: String,
    @SerializedName("sys")
    val additionalInfo: Sys,
    @SerializedName("weather")
    val weatherVisuals: List<Weather>

)