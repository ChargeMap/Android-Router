@file:Suppress("UNCHECKED_CAST")

package com.iodji.router

import com.iodji.router.destinations.RouteWithInit
import com.iodji.router.destinations.RouteWithParamAndInit

interface RouteInit

fun <I : RouteInit, R : RouteWithInit<I>> lazyRouteInit(destination: R): Lazy<I> = lazy { Router.findInit(destination) as I }
fun <I : RouteInit, R : RouteWithParamAndInit<*, I>> lazyRouteInit(destination: R): Lazy<I> = lazy { Router.findInit(destination) as I }