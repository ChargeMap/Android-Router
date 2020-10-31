package com.iodji.router.sample

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iodji.router.Router
import com.iodji.router.sample.databinding.ActivityButtonBinding

class SecondActivity : AppCompatActivity() {

    lateinit var ui: ActivityButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityButtonBinding.inflate(layoutInflater)

        processView()

        setContentView(ui.root)
    }

    private fun processView() {
        ui.root.setBackgroundColor(Color.BLUE)

        ui.mainButton.setOnClickListener {
            Router.of(this)
                .push(
                    Routes.Third,
                    Routes.Third.Bundle(
                        text = "Hello"
                    )
                )
        }
    }
}