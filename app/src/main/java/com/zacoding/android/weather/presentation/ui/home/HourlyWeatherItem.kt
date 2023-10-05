package com.zacoding.android.weather.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.zacoding.android.weather.presentation.model.HourlyWeatherViewDataModel
import com.zacoding.android.weather.presentation.model.factory.createHourlyWeather
import com.zacoding.android.weather.presentation.theme.WeatherAppTheme
import com.zacoding.android.weather.R

@Composable
fun HourlyWeatherItem(
    modifier: Modifier = Modifier,
    hourly: HourlyWeatherViewDataModel
) {
    Box(
        modifier = modifier.then(
            Modifier
                .width(64.dp)
                .fillMaxHeight()
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = hourly.hour,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1.copy(
                    color = Color.Black,
                    fontSize = 12.sp
                )
            )

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = hourly.icon)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.ic_cloud)
                            error(R.drawable.ic_cloud)
                        }).build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = hourly.temp,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )

                Text(
                    text = stringResource(R.string.temp),
                    modifier = Modifier,
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun HourlyWeatherItemPreview() {
    WeatherAppTheme {
        Surface {
            HourlyWeatherItem(
                modifier = Modifier.height(128.dp),
                hourly = createHourlyWeather()
            )
        }
    }
}
