package com.example.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import com.example.apisetup.BuildConfig
import com.example.apisetup.R
import com.example.model.headToHeadMatches.History
import com.example.model.headToHeadMatches.MatchInfo
import com.example.model.hotMatches.MatchStatusJ
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsCompanyComp
import com.example.model.odds.OddsRoot
import java.text.SimpleDateFormat
import java.util.*


object RegisterTools {

    fun emailOrPasswordOrFullNameIsEmptyErrorMassage(emailStr: String,passwordStr:String,fullNameStr:String,context: Context): String {
        var error_massage = ""
        if (emailStr == "" && passwordStr == "" && fullNameStr == "")
        {
            error_massage = context.getString(R.string.register_error_massage_1)
        }else{
            if (fullNameStr == "")
            {
                error_massage = context.getString(R.string.register_error_massage_2)
            }

            if (emailStr == "")
            {
                error_massage = context.getString(R.string.login_error_massage_2)
            }

            if (passwordStr == "")
            {
                error_massage = context.getString(R.string.login_error_massage_3)
            }
        }
        return error_massage
    }
    fun checkIfEmailOrPasswordOrFullNameIsEmpty(emailStr: String , passwordStr:String , fullNameStr:String): Boolean {
        var emptyOrNot = false
        if (emailStr != "" && passwordStr != ""  && fullNameStr != "")
        {
            emptyOrNot = true
        }else{
            if (emailStr == "" || passwordStr == "" || fullNameStr == "")
            {
                emptyOrNot = false
            }
        }
        return emptyOrNot
    }

    fun makeMapForRegisterRequirements(emailStr: String , passwordStr:String , fullNameStr:String): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["name"] = fullNameStr
        map["email"] = emailStr
        map["password"] = passwordStr
        map["phone_number"] = ""
        map["birthdate"] = ""
        map["gender"] = ""

        return map
    }

    fun checkIfEmailOrPasswordIsEmpty(emailStr: String,passwordStr:String): Boolean {
        var emptyOrNot = false
        if (emailStr != "" && passwordStr != "")
        {
            emptyOrNot = true
        }else{
            if (emailStr == "" || passwordStr == "")
            {
                emptyOrNot = false
            }
        }
        return emptyOrNot
    }

    fun emailOrPasswordIsEmptyErrorMassage(emailStr: String,passwordStr:String,context: Context): String {
        var error_massage = ""
        if (emailStr == "" && passwordStr == "")
        {
            error_massage = context.getString(R.string.login_error_massage_1)
        }else{
            if (emailStr == "")
            {
                error_massage = context.getString(R.string.login_error_massage_2)
            }

            if (passwordStr == "")
            {
                error_massage = context.getString(R.string.login_error_massage_3)
            }
        }
        return error_massage
    }

    fun makeMapForLoginRequirements(emailStr: String,passwordStr:String): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["email"] = emailStr
        map["password"] = passwordStr

        return map
    }

}