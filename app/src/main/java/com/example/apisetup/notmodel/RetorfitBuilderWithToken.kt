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

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(SharedPreferencesHelper.getAToken(context)!!))
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