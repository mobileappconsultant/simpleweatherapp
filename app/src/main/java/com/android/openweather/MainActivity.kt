package com.android.openweather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.ui.WeatherViewModel
import com.android.openweather.ui.composable.NoPermissionView
import com.android.openweather.ui.composable.WeatherList
import com.android.openweather.ui.theme.TestApplicationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@kotlin.OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
class MainActivity : ComponentActivity(), LocationListener {
    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestApplicationTheme {
                val locationPermissionState =
                    rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
                SideEffect {
                    locationPermissionState.launchPermissionRequest()
                }
                PermissionRequired(
                    permissionState = locationPermissionState,
                    permissionNotGrantedContent = {
                        NoPermissionView()
                    },
                    permissionNotAvailableContent = {
                        NoPermissionView()
                    }
                ) {
                    locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this)
                    val isRefreshing by viewModel.isRefreshing.collectAsState()

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing), onRefresh = { viewModel.refresh() }) {
                            val viewState = viewModel.weatherDataState.collectAsState()
                            Column(modifier = Modifier.fillMaxSize()) {
                                when (viewState.value) {
                                    is WeatherViewModel.ViewState.Data -> {
                                        val data = viewState.value as WeatherViewModel.ViewState.Data
                                        if (data.hasError) {
                                            Toast.makeText(
                                                LocalContext.current,
                                                "An error occurred",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        WeatherList(weathers = (viewState.value as WeatherViewModel.ViewState.Data).data)
                                    }
                                    WeatherViewModel.ViewState.Idle -> {
                                        // Nothing
                                    }
                                    WeatherViewModel.ViewState.Loading -> {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        viewModel.getWeatherDetails(WeatherRequest(lat = location.latitude, long = location.longitude))
        viewModel.setCurrentLocation(location)
        locationManager.removeUpdates(this)
    }
}
