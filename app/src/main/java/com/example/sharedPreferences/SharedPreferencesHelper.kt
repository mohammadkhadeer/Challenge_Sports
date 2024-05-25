package com.example.sharedPreferences

import android.content.Context
import com.example.model.login.UserData
import com.google.gson.Gson

object SharedPreferencesHelper {
    private const val PREFS_NAME = "USER_INFO"
    private const val USER_KEY = "user"

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
}