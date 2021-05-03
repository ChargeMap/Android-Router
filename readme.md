# Router

Router is a Android library which provides routing features to your applications.

![GitHub Package Registry version](https://img.shields.io/github/v/tag/chargemap/android-router?style=for-the-badge&label=Latest%20version&logo=github)

![GitHub Package Registry version](https://img.shields.io/badge/MIT-342e38?style=flat-square&label=License)
![GitHub Package Registry version](https://img.shields.io/badge/21+-342e38?style=flat-square&label=Minimum&logo=android)

## Installation

In your **root** *build.gradle* :

```
allprojects {
  repositories {
    ...
    maven(url = "https://maven.pkg.github.com/Chargemap/Android-Router")
  }
}
```
In your **module** *build.gradle* :

```bash
dependencies {
  implementation "com.chargemap.android:router:latestVersion"
}
```

## Usage

#### Declaration

```
object Routes {
    object Second : Route(path = "/second")
    object Third : RouteWithParam<Third.Bundle>(path = "/third") {
        @Parcelize
        class Bundle(
            val text: String
        ) : RouteParam
    }
}
```

#### Registration

```
Routes.Second.register {
    Intent(it, SecondActivity::class.java)
}
```

You are free to initialize your routes wherever you want but be sure to do it before pushing the associated route.

You may use [App Startup](https://developer.android.com/topic/libraries/app-startup) or the onCreate function of a custom application registered in your manifest (see sample).

#### Navigation

You can push a new route from either an **Activity** or a **Fragment**

For a simple route :

```
Router.of(this)
.push(
    Routes.Second
)

```

For a route with a param :

```
Router.of(this)
.push(
    Routes.Third,
    Routes.Third.Bundle(
        text = "Hello"
    )
)

```

## Acknowledgments

This library is inspired from [florent37/Navigator](https://github.com/florent37/Navigator)

## Contributors

| [![raphaël](https://github.com/r4phab.png?size=150)](https://github.com/r4phab) | [Raphaël Bertin](https://github.com/r4phab) |
|:------------------------------------------------------------------------------:|--------------|
