package com.android.openweather.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.openweather.data.network.request.WeatherRequest
import com.android.openweather.domain.WeatherMapper
import com.android.openweather.domain.model.WeatherDomain
import com.android.openweather.domain.usecases.local.DeleteWeatherFromDbUseCase
import com.android.openweather.domain.usecases.local.GetWeatherFromDbUseCase
import com.android.openweather.domain.usecases.local.InsertWeatherToDbUseCase
import com.android.openweather.domain.usecases.network.GetWeatherUseCase
import com.android.openweather.ui.model.Weather
import com.android.openweather.utils.AppConstant.DEFAULT_LOCATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherMapper: WeatherMapper,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherFromDbUseCase: GetWeatherFromDbUseCase,
    private val deleteWeatherFromDbUseCase: DeleteWeatherFromDbUseCase,
    private val insertWeatherToDbUseCase: InsertWeatherToDbUseCase,
) : ViewModel() {

    private val _weatherLiveData = MutableStateFlow<List<List<Weather>>>(listOf())

    private val _weatherDataState = MutableStateFlow<ViewState>(ViewState.Idle)

    private var currentLocation: Location? = null

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing = _isRefreshing.asStateFlow()

    val weatherDataState = _weatherDataState.asStateFlow()

    init {
        init()
    }

    fun getWeatherDetails(weatherRequest: WeatherRequest, refresh: Boolean = false) {
        _isRefreshing.value = refresh
        if (refresh.not()) {
            _weatherDataState.value = ViewState.Loading
        }
        viewModelScope.launch {
            getWeatherUseCase
                .execute(weatherRequest)
                .catch { e ->
                    _weatherDataState.value = ViewState.Data(hasError = true, _weatherLiveData.value)
                    Timber.e(e)
                    _isRefreshing.value = false
                }
                .collect {
                    insertIntoDb(it)
                    _isRefreshing.value = false
                }
        }
    }

    fun setCurrentLocation(location: Location) {
        currentLocation = location
    }

    private fun insertIntoDb(weatherList: Map<String, List<WeatherDomain>>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteWeatherFromDbUseCase.execute().collect()
                insertWeatherToDbUseCase.execute(weatherList.flatMap { it.value }).collect()
            }
        }
    }

    private fun init() {
        viewModelScope.launch {
            getWeatherFromDbUseCase.execute().collect {
                if (it.isEmpty().not()) {
                    _weatherLiveData.value = weatherMapper.mapToWeatherList(it)
                    _weatherDataState.value = ViewState.Data(data = _weatherLiveData.value)
                }
            }
        }
    }

    fun refresh() {
        val location = currentLocation ?: DEFAULT_LOCATION
        getWeatherDetails(WeatherRequest(location.latitude, location.longitude), true)
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val hasError: Boolean = false, val data: List<List<Weather>>) : ViewState()
        object Idle : ViewState()
    }
}
