package com.example.view.register

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

class RegisterActivity : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var cancel_full_name_rl: RelativeLayout
    private lateinit var cancel_email_rl: RelativeLayout
    private lateinit var cancel_password_rl: RelativeLayout
    private lateinit var create_account_rl: RelativeLayout
    private lateinit var sin_in_txt: TextView
    private lateinit var full_name_edt: EditText
    private lateinit var email_edt: EditText
    private lateinit var password_edt: EditText

    //value
    private var fullNameStr: String = ""
    private var emailStr: String = ""
    private var passwordStr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        statusBarColor()
        casting()
        actionListenerToBack()
        monitorEmailEdt()
        actionListenerToCancelEmailText()
        monitorPasswordEdt()
        actionListenerToCancelPasswordText()
        monitorFullNameEdt()
        actionListenerToCancelFullNameText()

        //Login button
        actionListenerToCreateAccount()
        //SinIn button
        actionListenerToMoveToSinIpActivity()
    }

    private fun actionListenerToMoveToSinIpActivity() {
        sin_in_txt.setOnClickListener {
            moveToSinInActivity()
        }
    }

    private fun moveToSinInActivity() {
        finish()
    }

    private fun actionListenerToCreateAccount() {
        create_account_rl.setOnClickListener {
            Log.i("TAG","TAG fullNameStr: "+ fullNameStr)
            Log.i("TAG","TAG email: "+ emailStr)
            Log.i("TAG","TAG password: "+ passwordStr)
        }
    }

    private fun actionListenerToCancelFullNameText() {
        cancel_full_name_rl.setOnClickListener {
            full_name_edt.text.clear()
            fullNameStr = ""
        }
    }

    private fun monitorFullNameEdt() {
        full_name_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that the text has been changed
                fullNameStr = text.toString()
                handleCancelFullNameTextButton(text)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleCancelFullNameTextButton(text: CharSequence?) {
        if (text!!.isEmpty())
        {
            cancel_full_name_rl.isVisible = false
        }else{
            cancel_full_name_rl.isVisible = true
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
    private fun actionListenerToBack() {
        back_image.setOnClickListener {
            finish()
        }
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    private fun casting() {
        back_image = findViewById<ImageView>(R.id.back_image)

        cancel_email_rl = findViewById<RelativeLayout>(R.id.cancel_email_rl)
        cancel_password_rl = findViewById<RelativeLayout>(R.id.cancel_password_rl)
        cancel_full_name_rl = findViewById<RelativeLayout>(R.id.cancel_full_name_rl)

        create_account_rl = findViewById<RelativeLayout>(R.id.craete_account_rl)

        sin_in_txt = findViewById<TextView>(R.id.sin_in_txt)

        full_name_edt = findViewById<EditText>(R.id.full_name_edt)
        email_edt = findViewById<EditText>(R.id.email_edt)
        password_edt = findViewById<EditText>(R.id.password_edt)
    }
}