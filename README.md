# CoinTicker &mdash; the Sample Coin Price Tracking App for Android

[Issues](https://github.com/mertcaliskanyurek/CoinTicker/issues)
[Request Feature](https://github.com/mertcaliskanyurek/CoinTicker/issues)

This is a simple app that provides realtime coin price tracking and coin details. Data provided by [Coingecko](https://www.coingecko.com/en/api)

<img src="/screenshots/Screenshot1.png" title="All Coins" width="200"> <img src="/screenshots/Screenshot2.png" title="Watchlist" width="200"> <img src="/screenshots/Screenshot3.png" title="Notification" width="200"><img src="/screenshots/Screenshot5.png" title="Coin Details" width="200"><img src="/screenshots/Screenshot6.png" title="Coin Details" width="200">

## About CoinTicker

* It allows to you see all coins and last prices against another currency witch is "vs_currency" in this project
* You can add a coin to watchlist. It's price will update every 10 secounds.

### What You Will Find in CoinTicker:
* How to use Hilt library for dependency injection
* How to use Room library and TypeConverters for data persistence
* How to implement a REST API using Retrofit and Gson libraries
* How to use Glide library for better image loading
* How to use SearchRecentSuggestionsProvider for better app search ux
* How to create ForegroundService 
* How to create Bottom Tabs 
* How to create BindingAdapters for your views

## Getting Started

### Open and Run Project in Android Studio

As usual, you get started by cloning the project to your local machine:

```
$ git clone https://github.com/mertcaliskanyurek/CoinTicker.git
```
Now that you have cloned the repo: open the project up in Android Studio.

## Android Version Targeting

CoinTicker is currently built to work with 32 (Android 12L). **However**, minimum SDK support is 21(Android Lollipop).

## Build Configuration
This project was build on JDK 1.8

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Libraries
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=Cj0KCQiApb2bBhDYARIsAChHC9tHMW2qwmVyOo2mRK_joqK2785z4QljTRA2GLq7-usN2rKf5ZeCd5QaAvfUEALw_wcB&gclsrc=aw.ds)
* [Retrofit](https://square.github.io/retrofit/)
* [Gson](https://github.com/google/gson)
* [Glide](https://github.com/bumptech/glide)

Please feel free to submit issues with any bugs or other unforseen issues you experience. We work diligently to ensure that the ```main``` branch is always bug-free and easy to clone and run from Android Studio. If you experience problems, open an issue describing the problem and how to reproduce it, and we'll be sure to take a look at it.

