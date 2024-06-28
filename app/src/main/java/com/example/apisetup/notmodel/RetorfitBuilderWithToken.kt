package com.example.apisetup.notmodel

import android.content.Context
import android.util.Log
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetorfitBuilderWithToken {

    var baseUrl = "https://sportsapi3.com"
    private var retrofit: Retrofit? = null


    fun getInstance(context: Context): ApiService {

        var token = SharedPreferencesHelper.getAToken(context)!!
        var language = SharedPreferencesHelper.getALanguage(context)!!
//        token = "388|dhMFRSwnpJifuir6u7p2f0cjjbTgJENPTWjOXQeS"
//        println("TAG token "+token)
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token,language))
            .build()

        if (retrofit == null){

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory( GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val apiService2 =  retrofit!!.create(ApiService::class.java)

        return apiService2

    }

}