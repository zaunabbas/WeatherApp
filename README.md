<h1 align="center">Weather Demo App</h1>
 
A simple Weather forecast demo app using [OpenWeatherMap API](https://openweathermap.org/) with modern Android tech-stacks and MVVM-Clean- Architecture build with Jetpack Compose.


## Download
Go to the [Download Link](https://drive.google.com/file/d/1qVkXiJYj34AfvbM-7W_ZjcfDV7pdDWT_/view?usp=sharing) to download the latest APK.

## Screenshots
<p align="center">
<img src="/preview/preview0.png" width="32%"/>
<img src="/preview/preview1.png" width="32%"/>
<img src="/preview/preview2.png" width="32%"/>
<img src="/preview/preview3.png" width="32%"/>
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21 (Android 5.0)
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- JetPack
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Jetpack Compose - Modern UI Tool Kit.
- Architecture
  - MVVM + Clean Architecture (View - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Lottie-Android](https://github.com/airbnb/lottie-android) - Render After Effects animations natively on Android
