package com.chargemap.android.router.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chargemap.android.router.Router
import com.chargemap.android.router.sample.databinding.ActivityButtonBinding

class SecondActivity : AppCompatActivity() {

    lateinit var ui: ActivityButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityButtonBinding.inflate(layoutInflater)

        processView()

        setContentView(ui.root)
    }

    private fun processView() {
        ui.mainButton.setOnClickListener {
            Router.of(this)
                .push(
                    Routes.Third,
                    Routes.Third.Bundle(
                        text = "Hello"
                    ),
                    object : Routes.Third.Init {
                        override fun onStart() {
                            Toast.makeText(this@SecondActivity, "Hello from second activity", Toast.LENGTH_LONG).show()
                        }
                    }
                )
        }
    }
}