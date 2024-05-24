package com.example.view.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.apisetup.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        statusBarColor()
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}