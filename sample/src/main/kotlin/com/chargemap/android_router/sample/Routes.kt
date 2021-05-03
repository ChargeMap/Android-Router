package com.chargemap.android_router.sample

import com.chargemap.android_router.RouteParam
import com.chargemap.android_router.destinations.Route
import com.chargemap.android_router.destinations.RouteWithParam
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