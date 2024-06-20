package com.example.view.updateBirthday

import android.app.DatePickerDialog
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
import android.widget.DatePicker
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
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateBirthdayActivity : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var title_txt: TextView
    private lateinit var update_rl: RelativeLayout
    private lateinit var error_txt: TextView
    private lateinit var progressBarBlue: ProgressBar
    private lateinit var datePicker: DatePicker

    //value
    var title: String = ""
    var contentTxt: String = ""
    var server_key: String = ""
    private var press_on_update: Boolean = true
    private val calendar = Calendar.getInstance()

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_birthday)

        statusBarColor()
        casting()
        receiveValuesFromPastActivity()
        actionListenerToBack()

        setAInitDate()
        actionListenerToUpdate()

        //server
        view_model = SpewViewModel.giveMeViewModelWithHeader(this@UpdateBirthdayActivity)
        observeAResponse()
    }

    private fun setAInitDate() {
        // Set initial date
        Log.i("TAG","contentTxt "+ contentTxt)
        var initialYear = 1992
        var initialMonth = 10 // June (months are 0-based in Calendar)
        var initialDay = 26
        if (contentTxt == getString(R.string.birthday_massage1))
        {
            datePicker.updateDate(initialYear, initialMonth, initialDay)
        }else{
            val date = contentTxt.split("-")
            initialDay = date[0].toInt()
            initialMonth = date[1].toInt()
            initialYear = date[2].toInt()
            initialMonth -= 1
            datePicker.updateDate(initialYear, initialMonth, initialDay)
        }


        datePicker.updateDate(initialYear, initialMonth, initialDay)
    }

    private fun getDateFromDatePicker(): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val myFormat = "dd-MM-yyyy" // Specify your date format
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())

        return sdf.format(calendar.time)
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

                press_on_update = false
                update_rl.setBackgroundResource(R.drawable.bg_8)
                progressBarBlue.isVisible = true

                val map = EditProfileTools.makeMapForUpdateNameRequirements(getDateFromDatePicker(),server_key)
                view_model.updateBasicInfoRequest(map)
            }
        }
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
        update_rl  = findViewById<RelativeLayout>(R.id.update_rl)
        error_txt  = findViewById<TextView>(R.id.error_txt)
        progressBarBlue = findViewById<ProgressBar>(R.id.progress_bar)
        datePicker = findViewById<DatePicker>(R.id.datePicker)
    }
}