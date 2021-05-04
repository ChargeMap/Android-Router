package com.chargemap.android.router.destinations

import com.chargemap.android.router.utils.PathMatcher

interface Destination {
    val path: String
    val pathMatcher: PathMatcher
}