package com.example.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.apisetup.R
import com.example.view.register.RegisterActivity

class Login : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView

    private lateinit var cancel_email_rl: RelativeLayout
    private lateinit var cancel_password_rl: RelativeLayout
    private lateinit var forgot_pass_rl: RelativeLayout
    private lateinit var login_rl: RelativeLayout
    private lateinit var sin_up_txt: TextView
    private lateinit var email_edt: EditText
    private lateinit var password_edt: EditText

    //value
    private var emailStr: String = ""
    private var passwordStr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        statusBarColor()
        casting()
        actionListenerToBack()
        monitorEmailEdt()
        monitorPasswordEdt()
        actionListenerToCancelEmailText()
        actionListenerToCancelPasswordText()

        //Login button
        actionListenerToLogin()
        //Forgot password button
        actionListenerToForgotPassword()
        //SinUp button
        actionListenerToMoveToSinUpActivity()
    }

    private fun actionListenerToMoveToSinUpActivity() {
        sin_up_txt.setOnClickListener {
            moveToSinUpActivity()
        }
    }

    private fun moveToSinUpActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun actionListenerToForgotPassword() {
        forgot_pass_rl.setOnClickListener {
            Log.i("TAG", "TAG forgot password: ")
        }
    }

    private fun actionListenerToLogin() {
        login_rl.setOnClickListener {
            Log.i("TAG","TAG email: "+ emailStr)
            Log.i("TAG","TAG password: "+ passwordStr)
        }
    }

    private fun actionListenerToCancelPasswordText() {
        cancel_password_rl.setOnClickListener {
            password_edt.text.clear()
            passwordStr = ""
        }
    }

    private fun monitorPasswordEdt() {
        password_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that the text has been changed
                passwordStr = text.toString()
                handleCancelPasswordTextButton(text)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleCancelPasswordTextButton(text: CharSequence?) {
        if (text!!.isEmpty())
        {
            cancel_password_rl.isVisible = false
        }else{
            cancel_password_rl.isVisible = true
        }
    }

    private fun actionListenerToCancelEmailText() {
        cancel_email_rl.setOnClickListener {
            email_edt.text.clear()
            emailStr = ""
        }
    }

    private fun monitorEmailEdt() {
        email_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that the text has been changed
                emailStr = text.toString()
                handleCancelEmailTextButton(text)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleCancelEmailTextButton(text: CharSequence?) {
        if (text!!.isEmpty())
        {
            cancel_email_rl.isVisible = false
        }else{
            cancel_email_rl.isVisible = true
        }
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    override fun finish() {
        super.finish()
        //overridePendingTransition(R.anim.up_to_bottom_enter, R.anim.up_to_bottom_exit)
    }

    private fun casting() {
        back_image = findViewById<ImageView>(R.id.back_image)

        cancel_email_rl = findViewById<RelativeLayout>(R.id.cancel_email_rl)
        cancel_password_rl = findViewById<RelativeLayout>(R.id.cancel_password_rl)
        forgot_pass_rl = findViewById<RelativeLayout>(R.id.forgot_pass_rl)
        login_rl = findViewById<RelativeLayout>(R.id.login_rl)

        sin_up_txt = findViewById<TextView>(R.id.sin_up_txt)

        email_edt = findViewById<EditText>(R.id.email_edt)
        password_edt = findViewById<EditText>(R.id.password_edt)
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener {
            finish()
        }
    }
}