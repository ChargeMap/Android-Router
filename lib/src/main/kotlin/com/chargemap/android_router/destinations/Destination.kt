package com.chargemap.android_router.destinations

import com.chargemap.android_router.utils.PathMatcher

interface Destination {
    val path: String
    val pathMatcher: PathMatcher
}