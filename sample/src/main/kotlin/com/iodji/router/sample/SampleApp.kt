package com.iodji.router.sample

import android.app.Application
import android.content.Intent

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Routes.Second.register {
            Intent(this, SecondActivity::class.java)
        }
    }
}