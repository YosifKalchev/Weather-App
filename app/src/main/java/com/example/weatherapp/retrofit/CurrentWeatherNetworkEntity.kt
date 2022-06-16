package com.example.weatherapp.retrofit


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherNetworkEntity(

    @Expose
    var html_attributions: List<String>,

    //todo get the exact fields
)
