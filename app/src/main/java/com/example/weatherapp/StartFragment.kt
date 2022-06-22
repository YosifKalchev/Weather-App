package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.api.models.CurrentWeatherApiResponse
import com.example.weatherapp.databinding.FragmentStartBinding
import com.example.weatherapp.viewmodels.CurrentWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.markodevcic.peko.Peko
import com.markodevcic.peko.PermissionResult
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding
    private var _binding: FragmentStartBinding? = null
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStartBinding.bind(view)
        _binding = binding
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        lateinit var currentWeatherDetails: CurrentWeatherApiResponse


        getLoc()
        lifecycleScope.launchWhenCreated {
            launch {
                val result = Peko.requestPermissionsAsync(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                if (result is PermissionResult.Granted) {
                    val currentLocation = mFusedLocationClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                CancellationTokenSource().token

                            override fun isCancellationRequested() = false
                        }
                    ).await()
                    setupScreen(
                        viewModel.getCurrentWeather(
                            currentLocation.latitude.toString(),
                            currentLocation.longitude.toString()
                        )
                    )

                } else {
                    requestPermission()
                }

            }
        }
    }


    private fun getLoc() {
        binding.sbSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //todo do something with the value
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                if (newText != "") {
                    try {
                        val addresses = geocoder.getFromLocationName(newText, 2)
                        val address = addresses[0]
                        lifecycleScope.launchWhenCreated {
                            setupScreen(
                                viewModel.getCurrentWeather(
                                    address.latitude.toString(), address.longitude.toString()
                                )
                            )
                        }
                    } catch (ex: Exception) {
                        Timber.e(ex.message)
                    }
                }
                return false
            }
        })
    }

    private fun setupScreen(current: CurrentWeatherApiResponse) {
        with(binding) {
            tvAddress.text = "${current.locationName}, ${current.additionalInfo.countryCode}"
            tvStatus.text = current.weatherVisuals[0].weatherDescription
            tvTemp.text = "${current.mainInfo.temperature.roundToInt()}°C"
            tvTempMin.text =
                "${getString(R.string.minTemp)}" +
                        "${current.mainInfo.minTemperature.roundToInt()}" +
                        "${getString(R.string.celsius)}"
            tvTempMax.text =
                "${getString(R.string.maxTemp)}" +
                        "${current.mainInfo.maxTemperature.roundToInt()}" +
                        "${getString(R.string.celsius)}"
            tvSunriseValue.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(Date(current.additionalInfo.sunriseTime.toLong() * 1000))
            tvSunsetValue.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(Date(current.additionalInfo.sunsetTime.toLong() * 1000))
            tvWindValue.text = "${current.wind.windSpeedInMs} ${getString(R.string.mPerS)}"
            tvPressureValue.text = "${current.mainInfo.pressure} ${getString(R.string.hPa)}"
            tvHumidityValue.text = "${current.mainInfo.humidity}%"

            tvUpdatedAt.text =
                "Updated at: ${
                    SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
                        .format(Date(current.weatherRequestTime.toLong() * 1000))
                }"

            Picasso.get().load(
                getString(R.string.ICON_PREFIX) +
                        current.weatherVisuals[0].weatherIcon +
                        getString(R.string.ICON_POSTFIX)
            ).into(imgLogo)
        }

    }

    private fun requestPermission() {
        if (isPermissionNotGranted()) {

            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )
            }
        }
    }

    private fun isPermissionNotGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}