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
import com.example.model.editProfile.EditProfileInfo
import com.example.model.headToHeadMatches.History
import com.example.model.headToHeadMatches.MatchInfo
import com.example.model.hotMatches.MatchStatusJ
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsCompanyComp
import com.example.model.odds.OddsRoot
import com.example.sharedPreferences.SharedPreferencesHelper
import java.text.SimpleDateFormat
import java.util.*


object EditProfileTools {

    fun makeMapForUpdateNameRequirements(value: String,key:String): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map[key] = value

        return map
    }

    fun fillProfileList(context: Context): ArrayList<EditProfileInfo>{
        var profileList: ArrayList<EditProfileInfo> = ArrayList()

        val userData = SharedPreferencesHelper.getProfileInfo(context)

        if(userData != null)
        {
            var birthday = ""
            var location = ""
            //message if dob is null
            if (userData.dob != null)
                birthday = userData.dob.toString()
            else
                birthday = context.getString(R.string.birthday_massage1)

            if (userData.country != null)
                location = userData.country.toString()
            else
                location = context.getString(R.string.location)

            profileList.add(EditProfileInfo(context.getString(R.string.full_name), userData!!.name,"name"))
            profileList.add(EditProfileInfo(context.getString(R.string.email), userData!!.email,"email_cant_be_updated"))
            profileList.add(EditProfileInfo(context.getString(R.string.birthday), birthday,"dob"))
            profileList.add(EditProfileInfo(context.getString(R.string.gender), userData!!.gender,"gender"))
            profileList.add(EditProfileInfo(context.getString(R.string.location), location,"country"))
            profileList.add(EditProfileInfo(context.getString(R.string.password), context.getString(R.string.re_password),"password"))
        }else{
            val userData = SharedPreferencesHelper.getUser(context)

            var birthday = ""
            var location = ""
            //message if dob is null
            if (userData?.dob != null)
                birthday = userData?.dob.toString()
            else
                birthday = context.getString(R.string.birthday_massage1)

            if (userData?.country != null)
                location = userData?.country.toString()
            else
                location = context.getString(R.string.location)

            profileList.add(EditProfileInfo(context.getString(R.string.full_name), userData!!.name,"name"))
            profileList.add(EditProfileInfo(context.getString(R.string.email), userData!!.email,"email_cant_be_updated"))
            profileList.add(EditProfileInfo(context.getString(R.string.birthday), birthday,"dob"))
            profileList.add(EditProfileInfo(context.getString(R.string.gender), userData!!.gender,"gender"))
            profileList.add(EditProfileInfo(context.getString(R.string.location), location,"country"))
            profileList.add(EditProfileInfo(context.getString(R.string.password), context.getString(R.string.re_password),"password"))
        }

        return profileList
    }

}