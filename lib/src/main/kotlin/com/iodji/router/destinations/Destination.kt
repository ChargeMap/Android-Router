package com.iodji.router.destinations

import com.iodji.router.utils.PathMatcher

interface Destination {
    val path: String
    val pathMatcher: PathMatcher
}