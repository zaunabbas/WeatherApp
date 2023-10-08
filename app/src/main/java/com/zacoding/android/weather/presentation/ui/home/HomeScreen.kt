package com.zacoding.android.weather.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.zacoding.android.weather.R
import com.zacoding.android.weather.presentation.model.CurrentWeatherViewDataModel
import com.zacoding.android.weather.presentation.theme.Purple200
import com.zacoding.android.weather.presentation.theme.SkyBlue
import com.zacoding.android.weather.presentation.theme.WeatherAppTheme
import com.zacoding.android.weather.presentation.ui.custom.Background
import com.zacoding.android.weather.presentation.ui.custom.ExceptionHandleView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    todayLazyListState: LazyListState = rememberLazyListState(),
) {

    val viewState by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val requestFocus = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    HomeScreenContent(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        homeViewState = viewState,
        closeSearchView = {
            viewModel.enableSearchView(false)
        },
        onSearchChange = {
            viewModel.onSearchInputChanged(it)
        },
        openSearchView = {
            viewModel.enableSearchView(true)
        },
        focusRequest = requestFocus,
        keyboardController = keyboardController,
        weatherLazyListState = todayLazyListState,
        actionSearch = {
            viewModel.getWeather(viewState.searchState.query)
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    homeViewState: HomeViewState = HomeViewState(),
    onSearchChange: ((String) -> Unit)? = null,
    closeSearchView: (() -> Unit)? = null,
    openSearchView: (() -> Unit)? = null,
    focusRequest: FocusRequester = remember { FocusRequester() },
    keyboardController: SoftwareKeyboardController? = null,
    actionSearch: (() -> Unit)? = null,
    weatherLazyListState: LazyListState,
) {

    val bgColor = SkyBlue

    Surface(modifier = Modifier.fillMaxSize()) {
        Background(
            modifier = Modifier.fillMaxSize(), background = bgColor
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    HomeTopAppBar(
                        searchQuery = homeViewState.searchState.query,
                        onSearchChange = onSearchChange,
                        showSearchView = homeViewState.searchState.enabled,
                        closeSearchView = closeSearchView,
                        openSearchView = openSearchView,
                        focusRequest = focusRequest,
                        keyboardController = keyboardController,
                        actionSearch = actionSearch,
                    )
                },
                modifier = modifier,
                backgroundColor = Color.Transparent,
                content = { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        val contentModifier = Modifier
                            .fillMaxSize()
                            .padding(all = 18.dp)

                        ExceptionHandleView(
                            state = homeViewState,
                            snackBarHostState = scaffoldState.snackbarHostState,
                        ) {
                            if (homeViewState.currentWeather != null) {
                                CurrentWeatherContent(
                                    modifier = contentModifier,
                                    currentWeather = homeViewState.currentWeather,
                                    weatherState = homeViewState.weatherState,
                                    weatherLazyListState = weatherLazyListState,
                                )
                            }
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun CurrentWeatherContent(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeatherViewDataModel,
    weatherState: WeatherState = WeatherState.Today(),
    weatherLazyListState: LazyListState,
) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(top = 2.dp)
                .height(78.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = currentWeather.currentTime,
                    modifier = Modifier,
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.Black,
                        fontSize = 12.sp,
                    ),
                )

                Column(
                    modifier = Modifier.wrapContentSize(align = Alignment.BottomStart),
                ) {
                    Text(
                        text = currentWeather.city,
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 14.sp,
                        ),
                    )

                    Text(
                        text = currentWeather.country,
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.Black,
                            fontSize = 24.sp,
                        ),
                    )
                }
            }
        }

        CurrentWeatherInfo(
            modifier = Modifier.padding(top = 15.dp),
            currentWeather = currentWeather,
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(R.string.today),
            style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontSize = 18.sp,
            ),
        )

        // Day View
        Box(
            modifier = Modifier.height(124.dp),
        ) {
            LazyRow(state = weatherLazyListState) {
                items(weatherState.weathers) { hourly ->
                    HourlyWeatherItem(hourly = hourly)
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherInfo(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeatherViewDataModel,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                Purple200
            ),
    ) {

        Column {

            val clipSpecs = LottieClipSpec.Progress(0.2f, 0.5f)

            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    currentWeather.currentIconAnimation
                )
            )

            LottieAnimation(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
                clipSpec = clipSpecs,
            )

            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = currentWeather.currentTemp,
                    modifier = Modifier
                        .padding(top = 2.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4.copy(
                        color = Color.Black,
                        fontSize = 62.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )

                Text(
                    text = stringResource(R.string.temp),
                    style = MaterialTheme.typography.h5.copy(
                        color = Color.Black,
                        fontSize = 25.sp,
                    ),
                )
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = currentWeather.description,
                style = MaterialTheme.typography.h5.copy(
                    color = Color.Black,
                    fontSize = 18.sp,
                ),
            )

            Row {

                Column(
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = stringResource(R.string.humidity),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 12.sp,
                        ),
                    )

                    Text(
                        text = currentWeather.humidity,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 24.sp,
                        ),
                    )
                }

                Column(
                    modifier = Modifier
                        //.fillMaxWidth()
                        .weight(1f),//.align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.wind),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 12.sp,
                        ),
                    )

                    Text(
                        text = currentWeather.wind,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 24.sp,
                        ),
                    )
                }
            }

            Row(Modifier.padding(15.dp)) {
                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .weight(1f),//.align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.visibility),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 12.sp,
                        ),
                    )

                    Text(
                        text = currentWeather.visibility,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 24.sp,
                        ),
                    )
                }

                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .weight(1f),
                    //.align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.real_feel),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 12.sp,
                        ),
                    )

                    Text(
                        text = currentWeather.realFeel,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.Black,
                            fontSize = 24.sp,
                        ),
                    )
                }

            }
        }
    }
}

/**
 * TopAppBar for the Home screen
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeTopAppBar(
    elevation: Dp = 0.dp,
    showSearchView: Boolean = false,
    searchQuery: String = "",
    onSearchChange: ((String) -> Unit)? = null,
    closeSearchView: (() -> Unit)? = null,
    openSearchView: (() -> Unit)? = null,
    actionSearch: (() -> Unit)? = null,
    focusRequest: FocusRequester = remember { FocusRequester() },
    keyboardController: SoftwareKeyboardController? = null,
) {
    if (showSearchView) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { closeSearchView?.invoke() },
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.close),
                        tint = Color.Black,
                    )
                }

                TextField(
                    modifier = Modifier
                        .focusRequester(focusRequest)
                        .weight(1f)
                        .background(color = Color.Transparent),
                    value = searchQuery,
                    shape = RoundedCornerShape(size = 0.dp),
                    onValueChange = { value ->
                        onSearchChange?.invoke(value)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_city),
                        )
                    },
                    trailingIcon = if (searchQuery.isNotEmpty()) {
                        {
                            IconButton(
                                onClick = { onSearchChange?.invoke("") },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = stringResource(R.string.remove),
                                    tint = MaterialTheme.colors.primary,
                                )
                            }
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            actionSearch?.invoke()
                            keyboardController?.hide()
                        },
                    ),
                )
            }
        }

        LaunchedEffect(keyboardController) {
            focusRequest.requestFocus()
        }
    } else {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 4.dp, top = 12.dp),
                    style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onPrimary),
                    textAlign = TextAlign.Center,
                )
            },
            /*navigationIcon = {
                IconButton(onClick = { closeSearchView?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu_drawer),
                        contentDescription = stringResource(R.string.menu),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            },*/
            actions = {
                IconButton(onClick = { openSearchView?.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_city),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = elevation,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun HomeTopAppBarPreview() {
    WeatherAppTheme {
        Surface {
            HomeTopAppBar()
        }
    }
}