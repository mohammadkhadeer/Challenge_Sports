package com.example.apisetup.notmodel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetorfitBuilder {
    var baseUrl = "https://sportsapi3.com/sportsapi/"
    private var retrofit: Retrofit? = null
    val retroClient:Retrofit?
    get() {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory( GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
    val apiService:ApiService = retroClient!!.create(ApiService::class.java)
}