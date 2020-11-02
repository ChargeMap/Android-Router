package com.iodji.router.starter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

sealed class StarterHandler {

    abstract val context: Context?
    abstract val activity: Activity?
    abstract fun start(intent: Intent)
    abstract fun startForResult(intent: Intent, code: Int)

    class ContextStarter(override val context: Context) : StarterHandler() {
        override val activity: Activity? = null

        override fun start(intent: Intent) {
            throw IllegalStateException("Cannot start activity with this Router context")
        }

        override fun startForResult(intent: Intent, code: Int) {
            throw IllegalStateException("Cannot start activity with this Router context")
        }
    }

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
}
