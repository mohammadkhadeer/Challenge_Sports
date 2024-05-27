package com.example.view.register

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.RegisterTools
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

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
    private lateinit var error_rl: RelativeLayout
    private lateinit var error_txt: TextView
    private lateinit var myCheckBox: CheckBox

    //value
    private var fullNameStr: String = ""
    private var emailStr: String = ""
    private var passwordStr: String = ""
    private var acceptLicence: Boolean = false
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis: Long = 3000 // 3 seconds

    //server
    private lateinit var view_model: MyViewModel
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
        //checkBox button
        actionListenerToCheckBox()

        //send server request
        view_model = SpewViewModel.giveMeViewModel(this)
        observeLoginResponse()
    }

    private fun observeLoginResponse() {
        view_model.login.observe(this){
            if (it.status== Status.SUCCESS){
                SharedPreferencesHelper.saveUser(this, it.data!!.response.data)
                finish()
            }else{
                Log.i("TAG" ,"data.status "+it.status)
                error_rl.isVisible = true
                error_txt.text = getString(R.string.massage_login_4)
                hideAErrorMessage()
            }
        }
    }

    private fun actionListenerToCreateAccount() {
        create_account_rl.setOnClickListener {

            if (RegisterTools.checkIfEmailOrPasswordOrFullNameIsEmpty(emailStr,passwordStr,fullNameStr))
            {
                if (acceptLicence){
                    val map = RegisterTools.makeMapForRegisterRequirements(emailStr,passwordStr,fullNameStr)
                    view_model.register(map)
                }else{
                    var errorMassageStr = getString(R.string.register_error_massage_3)
                    showErrorMassage(errorMassageStr)
                }

            }else{
                var errorMassageStr = RegisterTools.emailOrPasswordOrFullNameIsEmptyErrorMassage(emailStr,passwordStr,fullNameStr,this)
                showErrorMassage(errorMassageStr)
            }
        }

    }
    private fun actionListenerToCheckBox() =
        myCheckBox.setOnCheckedChangeListener { _, isChecked ->
            acceptLicence = if (isChecked) {
                // CheckBox is checked
                true
            } else {
                // CheckBox is unchecked
                false
            }
        }

    private fun actionListenerToMoveToSinIpActivity() {
        sin_in_txt.setOnClickListener {
            moveToSinInActivity()
        }
    }

    private fun moveToSinInActivity() {
        finish()
    }

    private fun showErrorMassage(massageStr: String) {
        error_rl.isVisible = true
        error_txt.text = massageStr
        hideAErrorMessage()
    }

    private fun hideAErrorMessage() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //timeLeftInMillis = millisUntilFinished
            }

            override fun onFinish() {
                error_rl.isVisible = false
            }
        }.start()
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
        error_rl = findViewById<RelativeLayout>(R.id.error_rl)
        error_txt = findViewById<TextView>(R.id.error_txt)
        create_account_rl = findViewById<RelativeLayout>(R.id.craete_account_rl)

        sin_in_txt = findViewById<TextView>(R.id.sin_in_txt)
        myCheckBox = findViewById(R.id.myCheckBox)
        full_name_edt = findViewById<EditText>(R.id.full_name_edt)
        email_edt = findViewById<EditText>(R.id.email_edt)
        password_edt = findViewById<EditText>(R.id.password_edt)
    }
}