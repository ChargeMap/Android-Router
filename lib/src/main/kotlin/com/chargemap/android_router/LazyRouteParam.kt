@file:Suppress("UNUSED_PARAMETER")

package com.chargemap.android_router

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.chargemap.android_router.destinations.RouteWithParam
import com.chargemap.android_router.destinations.RouteWithParamAndInit

interface RouteParam : Parcelable

fun <P : RouteParam, R : RouteWithParam<P>> Fragment.lazyRouteParam(destination: R): Lazy<P?> = lazy { arguments?.param() }
fun <P : RouteParam, R : RouteWithParam<P>> Fragment.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { arguments!!.paramNotNull() }
fun <P : RouteParam, R : RouteWithParam<P>> Activity.lazyRouteParam(destination: R): Lazy<P?> = lazy { intent.extras?.param() }
fun <P : RouteParam, R : RouteWithParam<P>> Activity.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { intent.extras!!.paramNotNull() }

fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Fragment.lazyRouteParam(destination: R): Lazy<P?> = lazy { arguments?.param() }
fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Fragment.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { arguments!!.paramNotNull() }
fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Activity.lazyRouteParam(destination: R): Lazy<P?> = lazy { intent.extras?.param() }
fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Activity.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { intent.extras!!.paramNotNull() }

private fun <P : RouteParam> Bundle.param(): P? = getBundle("bundle")?.getParcelable("routeParam")
private fun <P : RouteParam> Bundle.paramNotNull(): P = param()!!