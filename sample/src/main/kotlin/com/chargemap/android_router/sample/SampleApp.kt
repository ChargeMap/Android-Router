package com.chargemap.android_router.sample

import android.app.Application
import android.content.Intent

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Routes.Second.register {
            Intent(it, SecondActivity::class.java)
        }

        Routes.Third.register {
            Intent(it, ThirdActivity::class.java)
        }
    }
}