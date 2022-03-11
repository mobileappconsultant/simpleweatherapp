package com.android.openweather.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.android.openweather.ui.model.Weather

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherItem(weather: Weather) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(12.dp)
            .width(100.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter("https://openweathermap.org/img/w/${weather.weatherImage}.png"),
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
            Text(text = weather.weather)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "${weather.temperature} \u2103")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = weather.time)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun WeatherListItem(weathers: List<Weather>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = weathers.first().date, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(4.dp))
        Text(text = weathers.first().city, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(10.dp))
        LazyRow {
            items(weathers) { weather ->
                WeatherItem(weather)
            }
        }
    }
}
