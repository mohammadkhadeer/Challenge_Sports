package com.example.apisetup.notmodel

import com.example.model.hotMatches.HotMatchBaseClass
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(" api/football/matchlist/today")
    suspend fun getHotMatches(@Query("hotMatches") hotMatches:Boolean) : HotMatchBaseClass

    @GET(" api/football/matchlist/today?matchStatus=upcoming")
    suspend fun getUpcomingMatches() : HotMatchBaseClass

    @GET(" api/football/matchlist/today?matchStatus=finished")
    suspend fun getFinishedMatches() : HotMatchBaseClass

    @GET(" api/football/matchlist/today?matchStatus=live")
    suspend fun getLiveMatches() : HotMatchBaseClass

}