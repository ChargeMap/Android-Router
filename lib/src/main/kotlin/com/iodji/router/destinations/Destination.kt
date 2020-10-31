package com.iodji.router.destinations

import com.iodji.router.PathMatcher

interface Destination {
    val path: String
    val pathMatcher: PathMatcher
}