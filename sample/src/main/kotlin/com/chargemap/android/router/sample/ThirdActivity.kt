package com.chargemap.android.router.sample

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chargemap.android.router.lazyRouteInitNotNull
import com.chargemap.android.router.lazyRouteParamNotNull
import com.chargemap.android.router.sample.databinding.ActivityBinding

class ThirdActivity : AppCompatActivity() {

    private val routeParam by lazyRouteParamNotNull(Routes.Third)
    private val routeInit by lazyRouteInitNotNull(Routes.Third)

    lateinit var ui: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityBinding.inflate(layoutInflater)

        processView()

        setContentView(ui.root)

        routeInit.onStart()
    }

    private fun processView() {
        ui.root.setBackgroundColor(Color.CYAN)
        Toast.makeText(this, routeParam.text, Toast.LENGTH_LONG).show()
    }
}