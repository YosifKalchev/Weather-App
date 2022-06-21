package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
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
import com.google.android.gms.maps.model.LatLng
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
import java.lang.Exception
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

                    currentWeatherDetails = viewModel.getCurrentWeather(
                        "51.5072178",
                        "-0.12758619999999998"
//                        currentLocation.latitude.toString(),
//                        currentLocation.longitude.toString()
                    )
                    setupScreen(currentWeatherDetails)
                    getLocationData()
//                    setViewDataWithLocationParameters(currentLocation) //todo create this method

                    //todo setup the view with the API date here

                } else {
                    requestPermission()
                }
            }
        }
    }

    private suspend fun getLocationData() {
        var newLocation: LatLng? = null
        binding.sbSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location: String = binding.sbSearch.query.toString()
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                if (location == "") {
                    Toast.makeText(requireContext(), "provide location", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        val addresses = geocoder.getFromLocationName(location, 2)
                        val address = addresses[0]
//                        setupScreen(address)
                        newLocation = LatLng(address.latitude, address.longitude)

                        Timber.tag("loccation")
                            .e("Location: latitude ${address.latitude} ${address.longitude} ${address.locality}")

                    } catch (ex: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Enter a valid location name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        newLocation.let {
            setupScreen(viewModel.getCurrentWeather(
                it?.latitude.toString(),
                it?.longitude.toString()
            ))
        }
    }

    private fun setupScreen(currentPlace: CurrentWeatherApiResponse) {
        binding.tvAddress.text = "${currentPlace.name}, ${currentPlace.sys.country}"
        binding.tvStatus.text = currentPlace.weather[0].description
        binding.tvTemp.text = "${currentPlace.main.temp.roundToInt()}°C"
        binding.tvTempMin.text = "Min Temp:${currentPlace.main.temp_min.roundToInt()}°C"
        binding.tvTempMax.text = "Min Temp:${currentPlace.main.temp_max.roundToInt()}°C"
        binding.tvSunriseValue.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            .format(Date(currentPlace.sys.sunrise.toLong() * 1000))
        binding.tvSunsetValue.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            .format(Date(currentPlace.sys.sunset.toLong() * 1000))
        binding.tvWindValue.text = "${currentPlace.wind.speed} m/s"
        binding.tvPressureValue.text = "${currentPlace.main.pressure}hPa"
        binding.tvHumidityValue.text = "${currentPlace.main.humidity}%"

        binding.tvUpdatedAt.text =
            "Updated at: ${SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
                .format(Date(currentPlace.timeOfDataCalc.toLong()*1000))}"

        Picasso.get().load(
            getString(R.string.ICON_PREFIX) +
                    currentPlace.weather[0].icon +
                    getString(R.string.ICON_POSTFIX)
        ).into(binding.imgLogo)





        Timber.tag("picasso").e(
            getString(R.string.ICON_PREFIX) +
                    currentPlace.weather[0].icon +
                    getString(R.string.ICON_POSTFIX)
        )
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