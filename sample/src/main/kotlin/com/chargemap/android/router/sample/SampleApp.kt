package com.chargemap.android.router.sample

import android.app.Application
import com.chargemap.android.router.destinations.register

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Routes.Second.register<SecondActivity>()
        Routes.Third.register<ThirdActivity>()
    }
}