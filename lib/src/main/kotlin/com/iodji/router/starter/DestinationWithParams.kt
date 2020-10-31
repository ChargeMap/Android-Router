package com.iodji.router.starter

import com.iodji.router.destinations.Destination
import com.iodji.router.RouteCreator

data class DestinationWithParams(
    val destination: Destination,
    val params: Map<String, String>,
    val routeCreator: RouteCreator,
)