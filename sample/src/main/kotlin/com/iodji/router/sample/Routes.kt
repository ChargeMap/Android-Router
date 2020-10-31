package com.iodji.router.sample

import com.iodji.router.RouteParam
import com.iodji.router.destinations.Route
import com.iodji.router.destinations.RouteWithParam
import kotlinx.android.parcel.Parcelize

object Routes {
    object Second : Route(path = "/second")
    object Third : RouteWithParam<Third.Bundle>(path = "/third") {
        @Parcelize
        class Bundle(
            val text: String
        ) : RouteParam
    }
}