package com.example.view.splashScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.apisetup.R
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.GeneralTools
import com.example.utils.SharedPreference
import com.example.view.mainActivity.MainActivity
import com.example.view.userProfileActivity.UserProfileActivity
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity()  {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if(SharedPreferencesHelper.getALanguage(this) == null)
        {
            if (GeneralTools.getCurrentLanguage(this) == "en")
                SharedPreferencesHelper.saveLanguage(this,"en")
            else
                SharedPreferencesHelper.saveLanguage(this,"zh")
        }

        object : CountDownTimer(700,700){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }

        }.start()

    }


}