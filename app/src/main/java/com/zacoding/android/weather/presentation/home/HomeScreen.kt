package com.zacoding.android.weather.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.zacoding.android.weather.R
import com.zacoding.android.weather.data.Constants
import com.zacoding.android.weather.data.model.PokemonListEntry
import com.zacoding.android.weather.presentation.navigation.Screens
import com.zacoding.android.weather.presentation.theme.MaterialBlue
import com.zacoding.android.weather.presentation.theme.RobotoCondensed

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                hint = "Search...", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.getCurrentWeather(it)
            }
            CurrentWeather(
                navController = navController, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier, hint: String = "", onSearch: (String) -> Unit = {}
) {

    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isNotEmpty()
                })
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }

}

@Composable
fun CurrentWeather(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val currentWeather by remember { viewModel.currentWeather }
    val isLoading by remember { viewModel.isLoading }
    val loadingError by remember { viewModel.loadError }

    val weatherItem = currentWeather.weatherItems?.get(0)

    Box(
        contentAlignment = TopCenter,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1.5f)
            .background(
                MaterialBlue
            )
        /*.clickable {
            navController.navigate(
                Screens.PokemonDetailsScreen.withArgs(
                    //"${dominantColor.toArgb()}/${currentWeather.name}"
                )
            )
        }*/
    ) {
        Column {

            Row(
                modifier = Modifier.fillMaxWidth(), Arrangement.Center
            ) {
                Text(
                    text = "${currentWeather.main?.temp?.toInt()}",
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 62.sp,
                        fontFamily = RobotoCondensed
                    ),
                )

                Text(
                    text = stringResource(R.string.zero),
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 25.sp,
                        fontFamily = RobotoCondensed
                    ),
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = weatherItem?.description ?: "--",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 20.sp,
                    fontFamily = RobotoCondensed
                )
            )

            Row(
                modifier = Modifier
                    .padding(
                        start = 15.dp,
                        end = 15.dp
                    )
            ) {

                Column {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = "${stringResource(id = R.string.humidity_label)} ${currentWeather.main?.humidity}%",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 14.sp,
                            fontFamily = RobotoCondensed
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = String.format(
                            stringResource(id = R.string.wind_unit_label),
                            stringResource(id = R.string.wind_label),
                            currentWeather.wind?.speed
                        ),
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 14.sp,
                            fontFamily = RobotoCondensed
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(
                                data = String.format(
                                    Constants.OpenWeather.WEATHER_ICON_URL,
                                    weatherItem?.icon
                                )
                            ).apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                placeholder(R.drawable.ic_cloud)
                                error(R.drawable.ic_cloud)
                            }).build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        //.align(Center)
                        .size(84.dp),
                )


            }

            /* SubcomposeAsyncImage(
                 model = ImageRequest.Builder(LocalContext.current)
                     .data(currentWeather.name)
                     .crossfade(true)
                     .build(),
                 loading = {
                     CircularProgressIndicator(
                         color = MaterialTheme.colors.primary,
                         modifier = Modifier.scale(0.5f)
                     )
                 },
                 contentDescription = currentWeather.name,
                 onSuccess = {
                     *//*viewModel.calcDominantColor(it.result.drawable) { color ->
                        dominantColor = color
                    }*//*
                },
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )*/

        }

        //loading and Empty View
        Box(
            contentAlignment = Center, modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
            if (loadingError.isNotEmpty()) {
                RetrySection(error = loadingError) {
                    //viewModel.loadPokemonPaginated()
                }
            }
        }

    }

}

@Composable
fun PokemonList(
    navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val isLoading by remember { viewModel.isLoading }
    val loadingError by remember { viewModel.loadError }
    val isSearching by remember { viewModel.isSearching }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        val itemCount = pokemonList.size
        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                //viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }

}

@Composable
fun RetrySection(
    error: String, onRetry: () -> Unit
) {
    Column {

        Text(text = error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        /*Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }*/

    }

}

@Composable
fun PokemonEntry(
    currentWeather: PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    Box(contentAlignment = Center,
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor, defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    Screens.PokemonDetailsScreen.withArgs(
                        "${dominantColor.toArgb()}/${currentWeather.name}"
                    )
                )
            }) {
        Column {

            SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(currentWeather.imageUrl).crossfade(true).build(), loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary, modifier = Modifier.scale(0.5f)
                )
            }, contentDescription = currentWeather.name, onSuccess = {
                /*viewModel.calcDominantColor(it.result.drawable) { color ->
                    dominantColor = color
                }*/
            }, modifier = Modifier
                .size(120.dp)
                .align(CenterHorizontally)
            )
            Text(
                text = currentWeather.name,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }

    }

}

@Composable
fun PokedexRow(
    rowIndex: Int, entries: List<PokemonListEntry>, navController: NavController
) {
    Column {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            PokemonEntry(
                currentWeather = entries[rowIndex],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
        }
    }
}