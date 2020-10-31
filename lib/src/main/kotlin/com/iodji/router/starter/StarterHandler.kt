package com.iodji.router.starter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

sealed class StarterHandler {

    abstract val context: Context?
    abstract val activity: Activity?
    abstract fun start(intent: Intent)
    abstract fun startForResult(intent: Intent, code: Int)

    class ActivityStarter(override val activity: Activity) : StarterHandler() {
        override val context = activity

        override fun start(intent: Intent) {
            activity.startActivity(intent)
        }

        override fun startForResult(intent: Intent, code: Int) {
            activity.startActivityForResult(intent, code)
        }
    }

    class FragmentStarter(private val fragment: Fragment) : StarterHandler() {
        override val context = fragment.context
        override val activity: Activity? = fragment.activity

        override fun start(intent: Intent) {
            fragment.startActivity(intent)
        }

        override fun startForResult(intent: Intent, code: Int) {
            fragment.startActivityForResult(intent, code)
        }
    }

    class ApplicationStarter(val application: Application) : StarterHandler() {

        override val context = application
        override val activity: Activity? = null

        override fun start(intent: Intent) {
            activity?.startActivity(intent) ?: run {
                application.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            }
        }

        override fun startForResult(intent: Intent, code: Int) {
            activity?.startActivityForResult(intent, code) ?: run {
                //not possible to startForResult without an activity context
                application.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            }
        }
    }
}
