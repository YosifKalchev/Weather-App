package com.example.weatherapp.viewmodels

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.api.models.CurrentWeatherApiResponse
import com.example.weatherapp.repo.CurrentWeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val currentWeatherRepo: CurrentWeatherRepo
) : ViewModel() {

//    private lateinit var myNearbyPlaces: List<Result>

//    suspend fun getPlaces(
//        location: Location
//    ): List<Result> {
//        provideMyNearbyPlaces(location)
//        MyPlacesCollector.addItems(myNearbyPlaces)
//        return MyPlacesCollector.items
//    }
//
//
//    private suspend fun provideMyNearbyPlaces(
//        location: Location
//    ) {
//        val response = try {
//            getPlacesFromApi(location)
//        } catch (e: IOException) {
//            Timber.e("IOException, you might not have internet connection")
//            return
//        } catch (e: HttpException) {
//            Timber.e("HttpException, unexpected response")
//            return
//        }
//        if (response.isSuccessful && response.body() != null) {
//            myNearbyPlaces = response.body()!!.nearbyPlaces
//
//        } else {
//            Timber.e("Response not successful")
//        }
//    }

    private suspend fun getPlacesFromApi(
        location: Location
    ): Response<CurrentWeatherApiResponse> {

        return currentWeatherRepo.getPlaces("metric",
            "latitude", "longitude",
            BuildConfig.WEATHER_API_KEY
        )
    }


}

//  test coordinates
// "42.675589, 23.292616"




