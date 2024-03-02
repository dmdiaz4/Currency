<h1 align="center">Currency</h1>
<p align="center">
  💱 Simple app that uses https://api.vatcomply.com to show and convert currencies
</p>
<p align="center">
  <a href="https://opensource.org/licenses/mit"><img alt="License" src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
</p>

## Architecture, Tech stack & Open-source libraries

<img src="/app_architecture.png" align="right" width="320"/>

- **Minimum SDK level**
  - Android 24
  
- **Language**
   - [Kotlin](https://kotlinlang.org/)

- **UI Framework**
   - [Jetpack Compose](https://developer.android.com/jetpack/compose)
  
- **Architecture**
  - [MVI Architecture](https://developer.android.com/topic/architecture) (Model - View - Intent)
  - [Repository Pattern](https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754)
  - [Clean Code Architecture](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576) (Usecase + mappers)
    
- [Arrow-Kt](https://arrow-kt.io): Arrow brings idiomatic functional programming to Kotlin
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/): for asynchronous.
- [Retrofit](https://github.com/square/retrofit): A type-safe HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi/): A modern JSON serialization library for Kotlin.
- [Dagger/Hilt](https://github.com/google/dagger) : A fast dependency injector for Java and Android
- [Room](https://github.com/androidx-releases/Room): Persistence library that provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite
- [MockK](https://github.com/mockk/mockk) : A modern mocking library for Kotlin

----

## Screen Shots
<p align="center">
<img src="/screen_shot.png" width="35%"/>
<img src="/screen_shot2.png" width="35%"/>
</p>

<p align="center">
<img src="/screen_shot3.png" width="35%"/>
<img src="/screen_shot4.png" width="35%"/>
</p>


## License
    MIT License

    Copyright (c) 2024 David Diaz

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
