package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception
import java.util.*

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

                    getLocationData()
//                    setViewDataWithLocationParameters(currentLocation) //todo create this method

                    //todo setup the view with the API date here

                } else {
                    requestPermission()
                }
            }
        }
    }

    private fun getLocationData() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location: String = binding.searchView.query.toString()
                val geocoder = Geocoder(requireContext(), Locale.getDefault())

                if (location == "") {
                    Toast.makeText(requireContext(), "provide location", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        val addresses = geocoder.getFromLocationName(location, 2)
                        val address = addresses[0]
                        Timber.tag("loccation").e("Location: latitude ${address.latitude} ${address.longitude} ${address.locality}")
                    } catch (ex: Exception) {
                        Toast.makeText(requireContext(), "Enter a valid location name", Toast.LENGTH_SHORT).show()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
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

    //todo to be in viewModel

}