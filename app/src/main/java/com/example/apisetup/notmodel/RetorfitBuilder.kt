package com.example.apisetup.notmodel

import com.example.utils.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetorfitBuilder {
    private var slug="/"

    var privacyPolicy = "https://www.google.com/"
    var termsAndConditions = "https://www.google.com/"

    var baseUrl = "https://sportsapi3.com$slug"
    var adsBaseUrl = "https://api996.com/"
    private var retrofit: Retrofit? = null

    var token:String = ""

//    private val client = OkHttpClient.Builder()
//        .addInterceptor(AuthInterceptor(token!!))
//        .build()
    //                .client(client)

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