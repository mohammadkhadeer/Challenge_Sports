package com.example.view.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.apisetup.R
import com.example.utils.GeneralTools
import com.example.utils.SharedPreference
import com.example.view.mainActivity.MainActivity
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale()
        setContentView(R.layout.activity_splash_screen)

        object : CountDownTimer(1200,1200){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }

        }.start()


    }
    private fun setLocale() {
        val localLocale = Locale.getDefault().language
        if (localLocale.contains("zh")) {
            GeneralTools.setLocale(applicationContext, SharedPreference.CHINESE)
        }
        val languageToLoad = SharedPreference.getInstance().getStringValueFromPreference(
            SharedPreference.LOCALE_KEY,
            SharedPreference.CHINESE, this
        ) // your language
    }

}