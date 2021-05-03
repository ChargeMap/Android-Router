package com.chargemap.android_router.sample

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chargemap.android_router.lazyRouteParamNotNull
import com.chargemap.android_router.sample.databinding.ActivityBinding

class ThirdActivity : AppCompatActivity() {

    private val routeParam by lazyRouteParamNotNull(Routes.Third)

    lateinit var ui: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityBinding.inflate(layoutInflater)

        processView()

        setContentView(ui.root)
    }

    private fun processView() {
        ui.root.setBackgroundColor(Color.CYAN)

        Toast.makeText(this, routeParam.text, Toast.LENGTH_LONG).show()
    }
}