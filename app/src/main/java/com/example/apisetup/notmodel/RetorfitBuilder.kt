package com.example.apisetup.notmodel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetorfitBuilder {
    private var slug="/"
    var baseUrl = "https://sportsapi3.com$slug"
    var adsBaseUrl = "https://api996.com/"
    private var retrofit: Retrofit? = null

    var retroClient:Retrofit? = null
        get() {
            baseUrl = "https://sportsapi3.com$slug"
            if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory( GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }


    fun changeAPIAccess(accessSlugType:APIAccessSlug){
        retroClient=null
        slug = when(accessSlugType){
            APIAccessSlug.SPORTS_API -> {
                "/sportsapi/"
            }

            APIAccessSlug.CHALLENGES_API -> {
                "/challenges/"
            }
        }
    }
    val apiService:ApiService = retroClient!!.create(ApiService::class.java)
}