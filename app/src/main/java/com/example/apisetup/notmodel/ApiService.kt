package com.example.apisetup.notmodel

import com.example.model.hotMatches.HotMatchBaseClass
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(" api/football/matchlist/today")
    suspend fun getHotMatches(@Query("hotMatches") hotMatches:Boolean) : HotMatchBaseClass
}