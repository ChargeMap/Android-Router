@file:Suppress("UNCHECKED_CAST")

package com.iodji.router

import com.iodji.router.destinations.RouteWithInit
import com.iodji.router.destinations.RouteWithParamAndInit

interface RouteInit

fun <I : RouteInit, R : RouteWithInit<I>> lazyRouteInit(route: R): Lazy<I> = lazy { Router.findInit(route) as I }
fun <I : RouteInit, R : RouteWithParamAndInit<*, I>> lazyRouteInit(route: R): Lazy<I> = lazy { Router.findInit(route) as I }