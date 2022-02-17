@file:Suppress("UNUSED_PARAMETER")

package com.chargemap.android.router

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.chargemap.android.router.destinations.RouteWithParam
import com.chargemap.android.router.destinations.RouteWithParamAndInit

interface RouteParam : Parcelable

fun <P : RouteParam, R : RouteWithParam<P>> Fragment.lazyRouteParam(destination: R): Lazy<P?> = lazy { arguments?.param() }
fun <P : RouteParam, R : RouteWithParam<P>> Fragment.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { arguments!!.paramNotNull() }
fun <P : RouteParam, R : RouteWithParam<P>> Activity.lazyRouteParam(destination: R): Lazy<P?> = lazy { intent.extras?.param() }
fun <P : RouteParam, R : RouteWithParam<P>> Activity.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { intent.extras!!.paramNotNull() }

fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Fragment.lazyRouteParam(destination: R): Lazy<P?> = lazy { arguments?.param() }
fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Fragment.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { arguments!!.paramNotNull() }
fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Activity.lazyRouteParam(destination: R): Lazy<P?> = lazy { intent.extras?.param() }
fun <P : RouteParam, R : RouteWithParamAndInit<P, *>> Activity.lazyRouteParamNotNull(destination: R): Lazy<P> = lazy { intent.extras!!.paramNotNull() }

private fun <P : RouteParam> Bundle.param(): P? = getBundle(Router.bundleName)?.getParcelable(Router.bundleDataName)
private fun <P : RouteParam> Bundle.paramNotNull(): P = param()!!