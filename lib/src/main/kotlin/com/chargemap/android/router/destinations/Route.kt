package com.chargemap.android.router.destinations

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.chargemap.android.router.RouteInit
import com.chargemap.android.router.RouteParam
import com.chargemap.android.router.Router
import com.chargemap.android.router.utils.PathMatcher

sealed class AbstractRoute(
    override val path: String,
    val requestCode: Int? = null,
) : Destination {

    override val pathMatcher by lazy { PathMatcher(path) }

    open fun register(creator: (Context) -> Intent) {
        Router.register(this, creator, null)
    }

    fun register(classCreator: () -> Class<*>) {
        Router.register(this, {
            Intent(it, classCreator())
        }, null)
    }

    fun registerFragment(creator: () -> Fragment) {
        Router.register(this, creator)
    }
}

abstract class Route(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode)

abstract class RouteWithParam<P : RouteParam>(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode)

abstract class RouteWithParamAndInit<P : RouteParam, I : RouteInit>(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode)

abstract class RouteWithInit<I : RouteInit>(
    path: String,
    requestCode: Int? = null,
) : AbstractRoute(path, requestCode)

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

inline fun <reified T : Any> AbstractRoute.register() =
    register(
        classCreator = {
            T::class.java
        })