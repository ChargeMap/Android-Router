@file:Suppress("UNUSED_PARAMETER", "UNCHECKED_CAST")

package com.chargemap.android.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chargemap.android.router.destinations.*

class RouteCreator(
    val creator: (Context) -> Intent,
    val bundleCreator: ((Map<String, String>) -> RouteParam)? = null,
)

class FragmentCreator(
    val creator: () -> Fragment
)

object Router {

    private val fragments = mutableMapOf<Destination, FragmentCreator>()
    internal val routes = mutableMapOf<Destination, RouteCreator>()
    private val init = mutableMapOf<Destination, RouteInit>()

    fun reset() {
        fragments.clear()
        routes.clear()
        init.clear()
    }

    fun of(activity: Activity) = ActivityEngine(activity)
    fun of(fragment: Fragment) = FragmentEngine(fragment)

    fun has(route: Destination) =
        routes.toMap().containsKey(route) || fragments.toMap().containsKey(route)

    fun register(
        route: AbstractRoute,
        creator: (Context) -> Intent,
        bundleCreator: ((Map<String, String>) -> RouteParam)?
    ) {
        if (routes.containsKey(route)) throw Exception("route already registered ${route.path}")
        routes[route] = RouteCreator(creator, bundleCreator)
    }

    fun register(route: AbstractRoute, creator: () -> Fragment) {
        if (fragments.containsKey(route)) throw Exception("fragment already registered ${route.path}")
        fragments[route] = FragmentCreator(creator)
    }

    internal fun findInit(route: Destination): RouteInit {
        return init[route]
            ?: throw IllegalStateException("No init specified for route ${route.path}")
    }

    fun <T : Route> getIntent(context: Context, route: T): Intent {
        return routes[route]?.creator?.invoke(context)
            ?: throw Exception("route not registered ${route.path}")
    }

    fun <I : RouteInit, T : RouteWithInit<I>> getIntent(
        context: Context,
        route: T,
        routeInit: I?
    ): Intent {
        routeInit?.let {
            init[route] = it
        }
        return routes[route]?.creator?.invoke(context)
            ?: throw Exception("route not registered ${route.path}")
    }

    fun <P : RouteParam, T : RouteWithParam<P>> getIntent(
        context: Context,
        route: T,
        param: P?
    ): Intent {
        return routes[route]?.let {
            it.creator(context)
                .putExtra("bundle", Bundle().apply {
                    putParcelable("routeParam", param)
                })
        } ?: throw Exception("route not registered ${route.path}")
    }

    fun <P : RouteParam, I : RouteInit, T : RouteWithParamAndInit<P, I>> getIntent(
        context: Context,
        route: T,
        param: P?,
        routeInit: I?
    ): Intent {
        routeInit?.let {
            init[route] = routeInit
        }
        return routes[route]?.let {
            it.creator(context)
                .putExtra("bundle", Bundle().apply {
                    putParcelable("routeParam", param)
                })
        } ?: throw Exception("route not registered ${route.path}")
    }

    private fun findFragment(route: Destination) = fragments.toMap()[route]?.creator?.invoke()
        ?: throw Exception("fragment not registered ${route.path}")

    fun <T : Route> getFragment(route: T) = findFragment(route)

    fun <I : RouteInit, T : RouteWithInit<I>> getFragment(route: T, routeInit: I?): Fragment {
        routeInit?.let {
            init[route] = it
        }

        return findFragment(route)
    }

    fun <P : RouteParam, I : RouteInit, T : RouteWithParamAndInit<P, I>> getFragment(
        route: T,
        param: P,
        routeInit: I?
    ): Fragment {
        routeInit?.let {
            init[route] = it
        }

        return findFragment(route).apply {
            arguments = Bundle().apply {
                putBundle("bundle", Bundle().apply {
                    putParcelable("routeParam", param)
                })
            }
        }
    }

    fun <P : RouteParam, T : RouteWithParam<P>> getFragment(route: T, param: P) =
        findFragment(route).apply {
            arguments = Bundle().apply {
                putBundle("bundle", Bundle().apply {
                    putParcelable("routeParam", param)
                })
            }
        }
}