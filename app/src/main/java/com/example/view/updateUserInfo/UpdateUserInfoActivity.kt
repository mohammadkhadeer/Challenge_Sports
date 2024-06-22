package com.example.view.updateUserInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
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
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.apisetup.notmodel.RetorfitBuilderWithToken
import com.example.apisetup.notmodel.Status
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.utils.RegisterTools
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class UpdateUserInfoActivity : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var title_txt: TextView
    private lateinit var editText: EditText
    private lateinit var update_rl: RelativeLayout
    private lateinit var error_txt: TextView
    private lateinit var progressBarBlue: ProgressBar

    //value
    var title: String = ""
    var contentTxt: String = ""
    var server_key: String = ""
    private var press_on_update: Boolean = true

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_info)

        statusBarColor()
        casting()
        receiveValuesFromPastActivity()
        actionListenerToBack()
        openKeyboard()
        fillAHint()

        monitorEdt()
        actionListenerToUpdate()

        //server
        view_model = SpewViewModel.giveMeViewModelWithHeader(this@UpdateUserInfoActivity)
        observeAResponse()
    }

    private fun observeAResponse() {
        view_model.updateBasicInfo.observe(this){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                Toast.makeText(this, getString(R.string.update_successfully_message1) +" "+ title + " "+getString(R.string.update_successfully_message2), Toast.LENGTH_SHORT).show()
                Log.i("TAG","myData "+ it.data!!)
                SharedPreferencesHelper.saveProfileInfo(this, it.data!!.response.data)

                toldAPastActivityThisActivityFinish()

            }else{
                if (it.status == Status.ERROR){
                    //allow to user to try again
                    error_txt.isVisible = true
                    error_txt.text = getString(R.string.massage_login_4)

                    press_on_update = true
                    update_rl.setBackgroundResource(R.drawable.bg_5)
                    progressBarBlue.isVisible = false

                }

            }
        }
    }

    private fun toldAPastActivityThisActivityFinish() {
        val resultIntent = Intent()
        resultIntent.putExtra("result", "Hello from SecondActivity")
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun actionListenerToUpdate() {
        update_rl.setOnClickListener{
            if (press_on_update){
                if (contentTxt.isNotEmpty())
                {
                    press_on_update = false
                    update_rl.setBackgroundResource(R.drawable.bg_8)
                    progressBarBlue.isVisible = true

                    val map = EditProfileTools.makeMapForUpdateNameRequirements(contentTxt,server_key)
                    view_model.updateBasicInfoRequest(map)

                }else{
                    error_txt.text = getString(R.string.update_profile_error_m) + " " + title
                    error_txt.isVisible = true
                }
            }
        }
    }

    private fun monitorEdt() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that the text has been changed
                contentTxt = text.toString()
                error_txt.isVisible = false
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fillAHint() {
        editText.hint = title
        editText.setText(contentTxt)
    }

    private fun openKeyboard() {
        // Request focus and show keyboard
        editText.isFocusableInTouchMode = true
        editText.requestFocus()

        Handler(Looper.getMainLooper()).postDelayed({
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }, 100)
    }

    private fun receiveValuesFromPastActivity() {
        title = intent.getStringExtra("title")!!
        contentTxt = intent.getStringExtra("contentTxt")!!
        server_key = intent.getStringExtra("server_key")!!
        if (title != null) {
            title_txt.text = title
        }
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
        back_image = findViewById<ImageView>(R.id.back_image)
        title_txt  = findViewById<TextView>(R.id.title_txt)
        editText   = findViewById<EditText>(R.id.editText)
        update_rl  = findViewById<RelativeLayout>(R.id.update_rl)
        error_txt  = findViewById<TextView>(R.id.error_txt)
        progressBarBlue = findViewById<ProgressBar>(R.id.progress_bar)
    }

}