@file:Suppress("UNCHECKED_CAST")

package com.chargemap.android.router

import com.chargemap.android.router.destinations.RouteWithInit
import com.chargemap.android.router.destinations.RouteWithParamAndInit


fun <I : RouteInit, R : RouteWithInit<I>> lazyRouteInit(route: R): Lazy<I?> = lazy { Router.findInit(route) as? I }
fun <I : RouteInit, R : RouteWithParamAndInit<*, I>> lazyRouteInit(route: R): Lazy<I?> = lazy { Router.findInit(route) as? I }

fun <I : RouteInit, R : RouteWithInit<I>> lazyRouteInitNotNull(route: R): Lazy<I> = lazy { Router.findInit(route) as I }
fun <I : RouteInit, R : RouteWithParamAndInit<*, I>> lazyRouteInitNotNull(route: R): Lazy<I> = lazy { Router.findInit(route) as I }