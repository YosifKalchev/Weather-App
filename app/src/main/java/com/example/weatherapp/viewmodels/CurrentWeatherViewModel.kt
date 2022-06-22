package com.example.weatherapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.api.models.CurrentWeatherApiResponse
import com.example.weatherapp.repo.CurrentWeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val currentWeatherRepo: CurrentWeatherRepo
) : ViewModel() {

    private lateinit var currentWeatherDetails: CurrentWeatherApiResponse

    suspend fun getCurrentWeather(
        latitude: String, longitude: String
    ): CurrentWeatherApiResponse {
        viewModelScope.launch {  }
        provideCurrentWeather(latitude, longitude)
        return currentWeatherDetails
    }

    private suspend fun provideCurrentWeather(
        latitude: String, longitude: String
    ) {
        val response = try {
            getCurrentWeatherFromApi(latitude, longitude)
        } catch (e: IOException) {
            Timber.e("IOException, you might not have internet connection")
            return
        } catch (e: HttpException) {
            Timber.e("HttpException, unexpected response")
            return
        }
        if (response.isSuccessful && response.body() != null) {
            currentWeatherDetails = response.body()!!
        } else {
            Timber.e("Response not successful")
        }
    }

    private suspend fun getCurrentWeatherFromApi(
        latitude: String, longitude: String
    ): Response<CurrentWeatherApiResponse> {

        return currentWeatherRepo.getCurrentWeather(
            "metric", latitude, longitude,
            BuildConfig.WEATHER_API_KEY
        )
    }
}





