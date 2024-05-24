package com.example.view.login

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.apisetup.R

class Login : AppCompatActivity() {
    private lateinit var back_image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        statusBarColor()
        casting()
        actionListenerToBack()
    }
    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.up_to_bottom_enter, R.anim.up_to_bottom_exit)
    }

    private fun casting() {
        back_image = findViewById<ImageView>(R.id.back_image)
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }
}