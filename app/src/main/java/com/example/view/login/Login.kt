package com.example.view.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.RegisterTools
import com.example.view.bottomSheet.ForgotBottomSheetFragment
import com.example.view.bottomSheet.ModalBottomSheetFragment
import com.example.view.register.RegisterActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel


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
    private lateinit var error_rl: RelativeLayout
    private lateinit var error_txt: TextView
    private lateinit var loading_rl: RelativeLayout
    private lateinit var progress_bar: ProgressBar

    //value
    private var emailStr: String = ""
    private var passwordStr: String = ""

    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis: Long = 3000 // 3 seconds

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        statusBarColor()
        //casting ui and handel buttons
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

        //send server request
        view_model = SpewViewModel.giveMeViewModel(this)

        //after user press on login button observe the response
        observeLoginResponse()
    }

    private fun actionListenerToLogin() {
        login_rl.setOnClickListener {

            if (RegisterTools.checkIfEmailOrPasswordIsEmpty(emailStr,passwordStr))
            {
                loading_rl.isVisible = true
                val map = RegisterTools.makeMapForLoginRequirements(emailStr,passwordStr)
                view_model.login(map)
            }else{
                var errorMassageStr = RegisterTools.emailOrPasswordIsEmptyErrorMassage(emailStr,passwordStr,this)
                error_rl.isVisible = true
                error_txt.text = errorMassageStr
                hideAErrorMessage()
            }

        }
    }

    private fun observeLoginResponse() {
        view_model.login.observe(this){
            if (it.status== Status.SUCCESS){
                SharedPreferencesHelper.saveUser(this, it.data!!.response.data)
                finish()
            }else{
                if (it.status == Status.ERROR){
//                    Log.i("TAG" ,"it.message "+it.message)
                    loading_rl.isVisible = false
                    error_rl.isVisible = true
                    error_txt.text = getString(R.string.massage_login_4)
                    hideAErrorMessage()
                }

            }
        }
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
            val modalBottomSheet = ForgotBottomSheetFragment()
            modalBottomSheet.show(supportFragmentManager, ForgotBottomSheetFragment::class.java.simpleName)
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
        error_rl = findViewById<RelativeLayout>(R.id.error_rl)
        error_txt = findViewById<TextView>(R.id.error_txt)

        sin_up_txt = findViewById<TextView>(R.id.sin_up_txt)

        email_edt = findViewById<EditText>(R.id.email_edt)
        password_edt = findViewById<EditText>(R.id.password_edt)
        loading_rl = findViewById<RelativeLayout>(R.id.loading_rl)
        progress_bar = findViewById<ProgressBar>(R.id.progress_bar)

    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener {
            finish()
        }
    }
}