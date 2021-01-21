# devstarter-android-mvi

The Android application devstarter project template that can be used to start writing your application using the MVI architecture. 

## Stack

* [Kotlin](https://kotlinlang.org/)

### Libraries used / recommended

* [AndroidX](https://developer.android.com/jetpack/androidx) as support libraries
* [Dagger](https://dagger.dev/users-guide) for dependency injection 
* [Retrofit](https://square.github.io/retrofit) for HTTP networking
* [Gson](https://github.com/google/gson)/[Moshi](https://github.com/square/moshi) for JSON parsing/encoding
* [RxJava](https://github.com/ReactiveX/RxJava) for reactive handling of data streams
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) for platform-specific bindings (e.g. schedulers)
* [RxBinding](https://github.com/JakeWharton/RxBinding) for binding UI to rx streams
* [Room](https://developer.android.com/topic/libraries/architecture/room) as database 

### Libraries used / recommended for testing

* [JUnit4](https://junit.org/junit4/) and [JUnit5](https://junit.org/junit5/) for unit tests
* [Mockito](https://github.com/mockito/mockito/) and [MockK](https://mockk.io) for mocks
* [Espresso](https://developer.android.com/training/testing/espresso) for UI tests
* [Robolectric](robolectric.org/) for fast tests requiring Android framework but run on jvm
* [DeteKt](https://github.com/arturbosch/detekt) for static code analysis

### Libraries used / recommended for debugging
* [LeakCanary](https://square.github.io/leakcanary/) for catching memory leaks and retained objects 
* [Stetho](http://facebook.github.io/stetho/) for database / shared preferences / network debugging

### Crash logs and analytics

* [Firebase with Crashlytics](https://console.firebase.google.com/u/1/) for app analytics and crash logging

## Project setup

1. [Create a new repo using devstarter-android-mvi as a template](https://github.com/codequest-eu/devstarter-android-mvi/generate)
2. Clone it locally

   ```sh
   git clone <project_url>
   ```
   
3. Change a package name for the project structure

4. Align [config.gradle](./config.gradle)

5. Update a [README.md](./README.md)

6. Enjoy!

## Templates

As the MVI architecture used for presentation layer is quite complex, a helper templates solution has been provided.
Its main goal is to auto-generate all the boilerplate code that constitutes a fully-functional screen.

Templates solution was developerd with help of node.js [plop](https://plopjs.com/) module: . 

### Installation
Plop can be used in 2 ways: as a global npm module, or as a project dependency. 
We recommend the latter one, as not to interfere with other node.js projects. Sections below describe the prefered way.

Plop is included in the project 'package.json' file, so to install it simply execute:  
`npm install`  
in the root folder.

### Usage
Currently there is one plop _generator_ defined ('screen', see plopfile.js). It generates all the code of an empty MVI screen within a new package.

To generate the MVI boilerplate for 'newFeature' screen, follow instructions:
1. Change directory to the target directory of the package (eg. `./user/src/main/java/com/example/user/presentation` )
2. Execute plop: `npm run plop screen newFeature`
3. Enjoy content of `./user/src/main/java/com/example/user/presentation/newFeature`

(If you have installed plop globally, (2) would simplify to: `plop screen newFeature`)

## CI/CD

Example workflow for the [Jenkins](https://jenkins.io/) could be:

1. Run stage with `./gradlew clean` to clean the workspace before build
2. Run stage with `./gradlew assemble` to build the app
3. Run stage with `./gradlew detekt` to run static code analysis
4. Run stage with `./gradlew test` to run application unit tests
