package com.example.view.updatePassword

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.utils.RegisterTools
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class UpdatePasswordActivity : AppCompatActivity() {

    //ui
    private lateinit var back_image: ImageView
    private lateinit var editText_cp: EditText
    private lateinit var editText_np: EditText
    private lateinit var editText_cnp: EditText
    private lateinit var update_rl: RelativeLayout
    private lateinit var error_rl: RelativeLayout
    private lateinit var freezeRelative: RelativeLayout
    private lateinit var error_txt: TextView
    private lateinit var progress_bar: ProgressBar

    //value
    var current_password_str: String = ""
    var new_password_str: String = ""
    var confirm_new_password_str: String = ""
    private var press_on_update: Boolean = true
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis: Long = 3000 // 3 seconds

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        statusBarColor()
        casting()
        actionListenerToBack()
        monitorCPEdt()
        openKeyboard()
        monitorNPEdt()
        monitorCNPEdt()

        actionListenerToUpdate()

        //server
        view_model = SpewViewModel.giveMeViewModelWithHeader(this@UpdatePasswordActivity)
        observeAResponse()
    }

    private fun observeAResponse() {
        view_model.updatePass.observe(this){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                Toast.makeText(this, getString(R.string.update_password_message_6), Toast.LENGTH_SHORT).show()

                closeKeyBoard()
                finish()

            }else{
                if (it.status == Status.ERROR){
                    //allow to user to try again
                    freezeRelative.isVisible = false
                    progress_bar.isVisible = false

                    error_txt.text = getString(R.string.massage_login_4)
                    update_rl.setBackgroundResource(R.drawable.bg_5)
//                    progressBarBlue.isVisible = false
                }

            }
        }
    }

    private fun closeKeyBoard() {
        Handler(Looper.getMainLooper()).postDelayed({
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText_cp, InputMethodManager.SHOW_IMPLICIT)

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }, 100)
    }

    private fun actionListenerToUpdate() {
        update_rl.setOnClickListener{
            if (press_on_update){
                if (EditProfileTools.checkIfPasswordOrNewPasswordOrConfirmIsEmpty(current_password_str,new_password_str,confirm_new_password_str))
                {
                    if (EditProfileTools.checkIfEnteredPasswordMatchWithOldPassword(new_password_str,confirm_new_password_str))
                    {
                        press_on_update = false
                        update_rl.setBackgroundResource(R.drawable.bg_8)
                        freezeRelative.isVisible = true
                        progress_bar.isVisible = true

                        val map = EditProfileTools.makeMapForUpdatePasswordRequirements(current_password_str,new_password_str)
                        view_model.updatePassRequest(map)

                    }else{
                        showErrorMassage(getString(R.string.update_password_message_5))
                    }
                }else{
                    var errorMassageStr = EditProfileTools.passOrNewPassOrConfirmNewPassIsEmptyErrorMassage(current_password_str,new_password_str,confirm_new_password_str,this)
                    showErrorMassage(errorMassageStr)
                }
            }
        }
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
                println("im here")
                error_rl.isVisible = false
            }
        }.start()
    }

    private fun monitorCNPEdt() {
        editText_cnp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text!!.isEmpty())
                {
                    confirm_new_password_str = ""
                }else{
                    confirm_new_password_str = text.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun monitorNPEdt() {
        editText_np.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text!!.isEmpty())
                {
                    new_password_str = ""
                }else{
                    new_password_str = text.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    private fun monitorCPEdt() {
        editText_cp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text!!.isEmpty())
                {
                    current_password_str = ""
                }else{
                    current_password_str = text.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            finish()
        }
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun casting() {
        back_image     = findViewById<ImageView>(R.id.back_image)
        editText_cp    = findViewById<EditText>(R.id.editText_cp)
        editText_np    = findViewById<EditText>(R.id.editText_np)
        editText_cnp   = findViewById<EditText>(R.id.editText_cnp)
        update_rl      = findViewById<RelativeLayout>(R.id.update_rl)
        error_rl       = findViewById<RelativeLayout>(R.id.error_rl)
        freezeRelative = findViewById<RelativeLayout>(R.id.freezeRelative)
        error_txt      = findViewById<TextView>(R.id.error_txt)
        progress_bar   = findViewById<ProgressBar>(R.id.progress_bar)
    }

    private fun openKeyboard() {
        // Request focus and show keyboard
        editText_cp.isFocusableInTouchMode = true
        editText_cp.requestFocus()

        Handler(Looper.getMainLooper()).postDelayed({
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText_cp, InputMethodManager.SHOW_IMPLICIT)

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }, 100)
    }
}