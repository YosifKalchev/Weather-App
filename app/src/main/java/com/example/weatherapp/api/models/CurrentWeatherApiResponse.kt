package com.example.weatherapp.api.models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherApiResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,

    @SerializedName("coord")
    val coordinate: Coord,

    @SerializedName("dt")
    val timeOfDataCalc: Int,

    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)