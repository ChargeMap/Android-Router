package com.iodji.router.starter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iodji.router.RouteCreator
import com.iodji.router.RouteInit
import com.iodji.router.RouteParam
import com.iodji.router.Router
import com.iodji.router.destinations.*

typealias IntentConfig = (Intent) -> Unit

class NavigatorStarter(
    private val starterHandler: StarterHandler,
    private val routeCreator: Map<Destination, RouteCreator>,
    private val routeInit: MutableMap<Destination, RouteInit>,
) {

    private val context: Context get() = starterHandler.context ?: throw Exception("no context to push the route")

    fun <T : Route> push(route: T, finishActivity: Boolean = false, intentConfig: IntentConfig? = null) =
        startInternal(destination = route, requestCode = route.requestCode, intentConfig = intentConfig, finishActivity = finishActivity)

    fun <I : RouteInit, T : RouteWithInit<I>> push(route: T, init: I, finishActivity: Boolean = false, intentConfig: IntentConfig? = null) =
        startInternal(
            destination = route,
            requestCode = route.requestCode,
            intentConfig = intentConfig,
            finishActivity = finishActivity,
            init = init
        )

    fun <P : RouteParam, T : RouteWithParam<P>> push(
        route: T,
        arguments: P,
        finishActivity: Boolean = false,
        intentConfig: IntentConfig? = null,
    ): Boolean =
        startInternal(
            destination = route,
            init = null,
            requestCode = route.requestCode,
            intentConfig = intentConfig,
            finishActivity = finishActivity,
            routeParameter = arguments
        )

    fun <P : RouteParam, I : RouteInit, T : RouteWithParamAndInit<P, I>> push(
        route: T,
        arguments: P,
        init: I,
        finishActivity: Boolean = false,
        intentConfig: IntentConfig? = null,
    ): Boolean =
        startInternal(
            destination = route,
            requestCode = route.requestCode,
            intentConfig = intentConfig,
            finishActivity = finishActivity,
            routeParameter = arguments,
            init = init
        )

    fun <T : AbstractRoute> intent(
        destination: T,
        intentConfig: IntentConfig? = null,
    ): Intent {
        val containRoute = routeCreator.containsKey(destination)
        if (containRoute) {
            val intentCreator = routeCreator[destination]
            intentCreator?.let {
                val intent: Intent = it.creator(context)
                intentConfig?.invoke(intent)
                return it.creator(context)
            }
        }
        throw Exception("route not registered ${destination.path}")
    }

    private fun <T : AbstractRoute> startInternal(
        destination: T,
        requestCode: Int? = null,
        intentConfig: IntentConfig? = null,
        routeParameter: RouteParam? = null,
        finishActivity: Boolean = false,
        init: RouteInit? = null,
    ): Boolean {
        init?.let {
            routeInit[destination] = it
        }

        val containRoute = routeCreator.containsKey(destination)
        if (containRoute) {
            val intentCreator = routeCreator[destination]

            intentCreator?.let {

                val intent: Intent = it.creator(context)

                intent.putExtra("bundle", Bundle().apply {
                    putParcelable("routeParam", routeParameter)
                })

                intentConfig?.invoke(intent)

                if (requestCode == null) {
                    starterHandler.start(intent)
                } else {
                    starterHandler.startForResult(intent, requestCode)
                }

                if (finishActivity) {
                    starterHandler.activity?.finish()
                }
            } ?: throw Exception("route not registered ${destination.path}")
        } else throw Exception("route not registered ${destination.path}")
        return containRoute
    }

    fun push(path: String): Boolean = pushInternal(path = path)

    private fun pushInternal(path: String, resultCode: Int? = null, finishActivity: Boolean = false): Boolean {

        //try to find by name
        Router.findDestination(path = path)?.let { destination ->
            when (destination) {
                is Route -> {
                    push(destination, finishActivity = finishActivity)
                    return true
                }
                else -> { /* nothing to do */
                }
            }
        }

        //try to find by regex
        Router.findDestinationWithParams(path = path)?.let {
            pushWithParamsInternal(
                destinationWithParams = it,
                resultCode = resultCode,
                finishActivity = finishActivity
            )
            return true
        }
        return false
    }

    private fun pushWithParamsInternal(
        destinationWithParams: DestinationWithParams,
        resultCode: Int? = null,
        finishActivity: Boolean = false,
    ): Boolean {
        destinationWithParams.routeCreator.let {
            val intent: Intent = it.creator(context)

            intent.putExtra("bundle", Bundle().apply {
                it.bundleCreator?.let {
                    putParcelable("routeParam", it(destinationWithParams.params))
                }
            })

            if (resultCode == null) {
                starterHandler.start(intent)
            } else {
                starterHandler.startForResult(intent, resultCode)
            }

            if (finishActivity) {
                starterHandler.activity?.finish()
            }
        }

        return true
    }
}