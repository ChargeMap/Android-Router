@file:Suppress("UNUSED_PARAMETER", "UNCHECKED_CAST")

package com.chargemap.android.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chargemap.android.router.destinations.*

class RouteCreator(
    val factory: (Context) -> Intent,
    val bundleFactory: ((Map<String, String>) -> RouteParam)?
)

class FragmentCreator(
    val factory: () -> Fragment
)

interface RouteInit

object Router {

    const val bundleName = "__bundle__"
    const val bundleDataName = "__routeParam__"

    private val fragments = mutableMapOf<Destination, FragmentCreator>()
    private val routes = mutableMapOf<Destination, RouteCreator>()

    private val init = mutableMapOf<Destination, RouteInit?>()

    fun findRouteWithPath(path: String): Route? =
        routes
            .keys
            .firstOrNull {
                it.path == path && it is Route
            }?.let {
                it as Route
            }

    fun findRouteWithRegexp(path: String): Pair<Destination, RouteCreator>? =
        routes
            .keys
            .firstOrNull {
                it.pathMatcher.matches(url = path)
            }?.let {
                it to routes[it]!!
            }

    fun reset() {
        fragments.clear()
        routes.clear()
        init.clear()
    }

    fun of(activity: Activity) = ActivityEngine(activity)
    fun of(fragment: Fragment) = FragmentEngine(fragment)

    fun has(route: Destination) = routes.toMap().containsKey(route) || fragments.toMap().containsKey(route)

    fun register(
        route: AbstractRoute,
        creator: (Context) -> Intent,
        bundleCreator: ((Map<String, String>) -> RouteParam)?
    ) =
        when (routes.containsKey(route)) {
            true -> throw Exception("route already registered ${route.path}")
            else -> routes[route] = RouteCreator(creator, bundleCreator)
        }

    fun register(route: AbstractRoute, creator: () -> Fragment) =
        when (fragments.containsKey(route)) {
            true -> throw Exception("fragment already registered ${route.path}")
            else -> fragments[route] = FragmentCreator(creator)
        }

    internal fun findInit(route: Destination): RouteInit = init[route] ?: throw IllegalStateException("No init specified for route ${route.path}")

    fun <T : Route> getIntent(context: Context, route: T): Intent =
        routes[route]?.factory?.invoke(context)
            ?: throw Exception("route not registered ${route.path}")

    fun <I : RouteInit, T : RouteWithInit<I>> getIntent(
        context: Context,
        route: T,
        routeInit: I?
    ): Intent {
        init[route] = routeInit
        return routes[route]?.factory?.invoke(context)
            ?: throw Exception("route not registered ${route.path}")
    }

    fun <P : RouteParam, T : RouteWithParam<P>> getIntent(
        context: Context,
        route: T,
        param: P?
    ): Intent {
        return routes[route]?.let {
            it.factory(context)
                .putExtra(bundleName, Bundle().apply {
                    putParcelable(bundleDataName, param)
                })
        } ?: throw Exception("route not registered ${route.path}")
    }

    fun <P : RouteParam, I : RouteInit, T : RouteWithParamAndInit<P, I>> getIntent(
        context: Context,
        route: T,
        param: P?,
        routeInit: I?
    ): Intent {
        init[route] = routeInit
        return routes[route]?.let {
            it.factory(context)
                .putExtra(bundleName, Bundle().apply {
                    putParcelable(bundleDataName, param)
                })
        } ?: throw Exception("route not registered ${route.path}")
    }

    private fun findFragment(route: Destination) = fragments.toMap()[route]?.factory?.invoke()
        ?: throw Exception("fragment not registered ${route.path}")

    fun <T : Route> getFragment(route: T) = findFragment(route)

    fun <I : RouteInit, T : RouteWithInit<I>> getFragment(route: T, routeInit: I?): Fragment {
        init[route] = routeInit
        return findFragment(route)
    }

    fun <P : RouteParam, I : RouteInit, T : RouteWithParamAndInit<P, I>> getFragment(
        route: T,
        param: P,
        routeInit: I?
    ): Fragment {
        init[route] = routeInit
        return findFragment(route).apply {
            arguments = Bundle().apply {
                putBundle(bundleName, Bundle().apply {
                    putParcelable(bundleDataName, param)
                })
            }
        }
    }

    fun <P : RouteParam, T : RouteWithParam<P>> getFragment(route: T, param: P) =
        findFragment(route).apply {
            arguments = Bundle().apply {
                putBundle(bundleName, Bundle().apply {
                    putParcelable(bundleDataName, param)
                })
            }
        }
}