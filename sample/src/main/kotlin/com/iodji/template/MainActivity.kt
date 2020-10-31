package com.iodji.template

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iodji.template.databinding.ActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var ui: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityBinding.inflate(layoutInflater)

        processView()

        setContentView(ui.root)
    }

    private fun processView() {
        ui.root.setBackgroundColor(Color.RED)
    }
}