# Weather-App

![weather_app](https://user-images.githubusercontent.com/65896669/174929365-20af1d08-5144-4bca-978e-7d9e76888f02.jpg)

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.5.20-blue.svg)](https://kotlinlang.org)
[![AGP](https://img.shields.io/badge/AGP-4-blue?style=flat)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-7-blue?style=flat)](https://gradle.org)

[![codebeat badge](https://codebeat.co/badges/e9f1a825-b5bd-4c7a-aadc-7c8d0cf59310)](https://codebeat.co/projects/github-com-igorwojda-android-showcase-main)
[![CodeFactor](https://www.codefactor.io/repository/github/igorwojda/android-showcase/badge)](https://www.codefactor.io/repository/github/igorwojda/android-showcase)

Showcase is a sample project that presents a modern approach to [Android](https://en.wikipedia.org/wiki/Android_(operating_system)) application development.

The goal of the project is to create Android mobile application with the ability to display current weather conditions.
* Create android screen that has search bar on top. When the user enters city name display the current weather conditions in this city bellow the search bar. The customer should be able to change city name and search for another city.
* Detect current location of the customer. When the customer start the application, his location must be automatically detected using the device GPS. Then the city name is prepopulated in the search bar and the current weather conditions are shown without any customer interaction. The customer should be able to change city name and search for another city. 

## Project characteristics and tech-stack

<img src="misc/image/application_anim.gif" width="336" align="right" hspace="20">

This project takes advantage of best practices, many popular libraries and tools in the Android ecosystem. Most of the libraries are in the stable version unless there is a good reason to use non-stable dependency.

* Tech-stack
    * [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Room](https://developer.android.com/jetpack/androidx/releases/room) - store offline cache
    * [Hilt](https://dagger.dev/hilt/) - dependency injection
    * [Coil](https://github.com/coil-kt/coil) - image loading library

* Modern Architecture
    * Clean Architecture (at feature module level)
    * Single activity architecture using [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
  * [GitHub Actions](https://github.com/features/actions)
  * Automatic PR verification including tests, linters and 3rd online tools
* UI
    * [Material design](https://material.io/design)
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
    * Custom tasks
    * Plugins ([SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args),
    [android-junit5](https://github.com/mannodermaus/android-junit5))
    * [Dependency locks](https://docs.gradle.org/current/userguide/dependency_locking.html)
    * [Versions catalog](https://docs.gradle.org/7.0-milestone-1/userguide/platforms.html)

## Upcoming improvements

* Create forecast for the next few days.
* Add custom locations to favourite

## Getting started

There are a few ways to open this project.

### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Enter `https://github.com/YosifKalchev/Weather-App` into URL field an press `Clone` button

### Command-line + Android Studio

1. Run `https://github.com/YosifKalchev/Weather-App.git` command to clone project
2. Open `Android Studio` and select `File | Open...` from the menu. Select cloned directory and press `Open` button

## Author

[![Follow me](https://img.shields.io/twitter/follow/YosifKalchev?style=social)](https://twitter.com/yosifkalchev)

## License
```
MIT License

Copyright (c) 2022 Yosif Kalchev

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
