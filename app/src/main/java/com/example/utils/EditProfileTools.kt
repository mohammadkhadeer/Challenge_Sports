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
    fun fillProfileList(context: Context): ArrayList<EditProfileInfo>{
        val userData = SharedPreferencesHelper.getUser(context)

        var profileList: ArrayList<EditProfileInfo> = ArrayList()

        profileList.add(EditProfileInfo(context.getString(R.string.full_name), userData!!.name))
        profileList.add(EditProfileInfo(context.getString(R.string.email), userData!!.email))
        profileList.add(EditProfileInfo(context.getString(R.string.birthday), "birthday"))
        profileList.add(EditProfileInfo(context.getString(R.string.gender), userData!!.gender))
        profileList.add(EditProfileInfo(context.getString(R.string.location), "location"))
        profileList.add(EditProfileInfo(context.getString(R.string.password), context.getString(R.string.re_password)))


        return profileList
    }

}