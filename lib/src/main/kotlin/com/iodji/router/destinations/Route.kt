package com.iodji.router.destinations

import android.content.Context
import android.content.Intent
import com.iodji.router.PathMatcher
import com.iodji.router.RouteInit
import com.iodji.router.RouteParam
import com.iodji.router.Router

abstract class AbstractRoute(
    override val path: String,
    val requestCode: Int? = null,
) : Destination {

    override val pathMatcher by lazy { PathMatcher(path) }
}

abstract class Route(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode) {

    open fun register(creator: Context.() -> Intent) {
        Router.register(this, creator, null)
    }
}

abstract class RouteWithParam<P : RouteParam>(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode) {

    open fun register(creator: (Context) -> Intent) {
        Router.register(this, creator, null)
    }
}

abstract class RouteWithParamAndInit<P : RouteParam, I : RouteInit>(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode) {

    open fun register(creator: (Context) -> Intent) {
        Router.register(this, creator, null)
    }
}

abstract class RouteWithInit<I : RouteInit>(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode) {

    open fun register(creator: (Context) -> Intent) {
        Router.register(this, creator, null)
    }
}

abstract class RouteWithDeepLink<P : RouteParam>(
    path: String,
    requestCode: Int? = null,
    private val deepLink: String? = null,
    private val deepLinkMapper: ((Map<String, String>) -> P)? = null,
) : RouteWithParam<P>(path, requestCode) {

    override fun register(creator: (Context) -> Intent) {
        super.register(creator)

        if (deepLink != null && deepLinkMapper != null) {
            Router.register(object : Route(deepLink) {}, creator, deepLinkMapper)
        }
    }
}