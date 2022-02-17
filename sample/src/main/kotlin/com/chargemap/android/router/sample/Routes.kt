package com.chargemap.android.router.sample

import com.chargemap.android.router.RouteInit
import com.chargemap.android.router.RouteParam
import com.chargemap.android.router.destinations.Route
import com.chargemap.android.router.destinations.RouteWithParamAndInit
import kotlinx.parcelize.Parcelize

object Routes {
    object Second : Route(path = "/second")

    object Third : RouteWithParamAndInit<Third.Bundle, Third.Init>(path = "/third") {
        @Parcelize
        class Bundle(
            val text: String
        ) : RouteParam

        interface Init : RouteInit {
            fun onStart()
        }
    }
}