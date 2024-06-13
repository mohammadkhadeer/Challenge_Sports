package com.example.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.model.editProfile.serverModel.UserUpdateInfo
import com.example.model.login.UserData
import com.google.gson.Gson

object SharedPreferencesHelper {
    private const val PROFILE_INFO = "PROFILE_INFO"
    private const val PREFS_NAME = "USER_INFO"
    private const val USER_KEY = "user"
    private const val USER_INFO = "user_info"
    private var sharedPreferences: SharedPreferences? = null

    private const val LANGUAGE = "LANGUAGE"

    fun saveProfileInfo(context: Context, user: com.example.model.editProfile.serverModel.UserData) {
        val prefs = context.getSharedPreferences(PROFILE_INFO, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(user)
        prefs.edit().putString(USER_INFO, json).apply()
    }

    fun getProfileInfo(context: Context): com.example.model.editProfile.serverModel.UserData? {
        val prefs = context.getSharedPreferences(PROFILE_INFO, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(USER_INFO, null)
        return gson.fromJson(json, com.example.model.editProfile.serverModel.UserData::class.java)
    }

    fun clearProfileInfo(context: Context) {
        getSharedPreferencesProfileInfo(context)
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }

    // Save user object to SharedPreferences
    fun saveUser(context: Context, user: UserData) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(user)
        prefs.edit().putString(USER_KEY, json).apply()
    }

    // Retrieve user object from SharedPreferences
    fun getUser(context: Context): UserData? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(USER_KEY, null)
        return gson.fromJson(json, UserData::class.java)
    }

    private fun getSharedPreferencesObject(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun getSharedPreferencesProfileInfo(context: Context) {
        sharedPreferences = context.getSharedPreferences(PROFILE_INFO, Context.MODE_PRIVATE)
    }

    fun clearData(context: Context) {
        getSharedPreferencesObject(context)
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }

    fun saveLanguage(context: Context, value: String) {
        getSharedPreferencesObject(context)
        val editor = sharedPreferences!!.edit()
        editor.putString(LANGUAGE, value)
        editor.apply() // or editor.commit() if you need to save synchronously
    }

    fun getALanguage(context: Context): String? {
        getSharedPreferencesObject(context)
        return sharedPreferences!!.getString(LANGUAGE, null)
    }

    fun getAToken(context: Context): String? {
        getSharedPreferencesObject(context)
        val userData = getUser(context)
        return userData!!.token
    }

    fun getABio(context: Context): String? {
        getSharedPreferencesProfileInfo(context)
        val userData = getProfileInfo(context)

        var xx:String? = if (userData!!.about != null)
            return userData!!.about.toString()
        else
            return null

    }

    fun getGender(context: Context): String? {
        getSharedPreferencesProfileInfo(context)
        val userData = getProfileInfo(context)

        var xx:String? = if (userData!!.gender != null)
            return userData!!.about.toString()
        else
            return null

    }}