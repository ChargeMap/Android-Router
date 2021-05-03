@file:Suppress("UNCHECKED_CAST")

package com.chargemap.android_router

import com.chargemap.android_router.destinations.RouteWithInit
import com.chargemap.android_router.destinations.RouteWithParamAndInit

interface RouteInit

fun <I : RouteInit, R : RouteWithInit<I>> lazyRouteInit(route: R): Lazy<I> = lazy { Router.findInit(route) as I }
fun <I : RouteInit, R : RouteWithParamAndInit<*, I>> lazyRouteInit(route: R): Lazy<I> = lazy { Router.findInit(route) as I }