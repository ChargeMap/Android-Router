@file:Suppress("UNUSED_PARAMETER", "UNCHECKED_CAST")

package com.iodji.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iodji.router.destinations.AbstractRoute
import com.iodji.router.destinations.Destination
import com.iodji.router.destinations.Route
import com.iodji.router.destinations.RouteWithParam
import com.iodji.router.starter.DestinationWithParams
import com.iodji.router.starter.NavigatorStarter
import com.iodji.router.starter.StarterHandler

class RouteCreator(
    val creator: (Context) -> Intent,
    val bundleCreator: ((Map<String, String>) -> RouteParam)? = null,
)

class FragmentCreator(
    val creator: () -> Fragment
)

object Router {

    private val fragments = mutableMapOf<Destination, FragmentCreator>()
    private val routes = mutableMapOf<Destination, RouteCreator>()
    private val init = mutableMapOf<Destination, RouteInit>()

    fun of(fragment: Activity) = NavigatorStarter(StarterHandler.ActivityStarter(fragment), routes.toMap(), init)
    fun of(fragment: Fragment) = NavigatorStarter(StarterHandler.FragmentStarter(fragment), routes.toMap(), init)

    fun has(route: Destination) = routes.toMap().containsKey(route) || fragments.toMap().containsKey(route)

    internal fun findInit(path: Destination): RouteInit {
        return init[path] ?: throw IllegalStateException("No init specified for route $path")
    }

    internal fun findDestination(path: String): Destination? {
        routes.forEach {
            val route = it.key
            if (it.key.path == path) {
                return route
            }
        }
        return null
    }

    internal fun findDestinationWithParams(path: String): DestinationWithParams? {
        //try with routes
        for ((destination, routing) in routes) {
            if (destination.pathMatcher.matches(url = path)) {
                val parametersValues = destination.pathMatcher.parametersValues(url = path)
                return DestinationWithParams(
                    destination = destination,
                    params = parametersValues,
                    routeCreator = routing
                )
            }
        }
        return null
    }

    fun register(route: AbstractRoute, creator: (Context) -> Intent, bundleCreator: ((Map<String, String>) -> RouteParam)?) {
        if (routes.containsKey(route)) throw Exception("route already registered ${route.path}")
        routes[route] = RouteCreator(creator, bundleCreator)
    }

    fun register(route: AbstractRoute, creator: () -> Fragment) {
        if (fragments.containsKey(route)) throw Exception("fragment already registered ${route.path}")
        fragments[route] = FragmentCreator(creator)
    }

    fun <T : Route> getFragment(route: T) = fragments.toMap()[route]?.creator?.invoke() ?: throw Exception("fragment not registered ${route.path}")

    fun <P : RouteParam, T : RouteWithParam<P>> getFragment(route: T, param: P) =
        fragments.toMap()[route]?.creator?.invoke()?.apply {
            arguments = Bundle().apply {
                putBundle("bundle", Bundle().apply {
                    putParcelable("routeParam", param)
                })
            }
        } ?: throw Exception("fragment not registered ${route.path}")
}