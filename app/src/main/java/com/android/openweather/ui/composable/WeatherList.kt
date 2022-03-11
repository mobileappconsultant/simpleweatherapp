package com.android.openweather.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.android.openweather.ui.model.Weather

@Composable
fun WeatherList(weathers: List<List<Weather>>) {
    if (weathers.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Weather Data Found")
        }
    } else {
        Column() {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
                    Text(
                        text = weathers.first().first().city,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            LazyColumn {
                items(weathers) { weather ->
                    WeatherListItem(weather)
                }
            }
        }
    }
}
