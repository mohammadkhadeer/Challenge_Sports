package com.example.apisetup.notmodel

import com.example.model.headToHeadMatches.H2HRoot
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.login.LogInRoot
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsRoot
import com.example.model.videos.random.RandomVidsBase
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET(" sportsapi/api/football/matchlist/today")
    suspend fun getHotMatches(@Query("hotMatches") hotMatches:Boolean) : HotMatchBaseClass

    @GET(" sportsapi/api/football/matchlist/today?matchStatus=upcoming")
    suspend fun getUpcomingMatches() : HotMatchBaseClass

    @GET(" sportsapi/api/football/matchlist/today?matchStatus=finished")
    suspend fun getFinishedMatches() : HotMatchBaseClass

    @GET(" sportsapi/api/football/matchlist/today?matchStatus=live")
    suspend fun getLiveMatches() : HotMatchBaseClass

    @GET(" sportsapi/api/football/match/odd/detail/withcompany/{matchid}")
    suspend fun getMatchOddList(@Path("matchid") matchid:String) : List<OddsRoot>

    @GET(" sportsapi/api/football/match-analysis/statics/{matchid}")
    suspend fun getH2HListMatches(@Path("matchid") matchid:String) : H2HRoot

    @GET(" challenges/api/challange-videos/get")
    suspend fun getVideos(@Query("type") type: String): RandomVidsBase

    @POST(" challenges/api/auth/login")
    suspend fun login(@Body userData: HashMap<String, Any>): LogInRoot

    @POST(" challenges/api/auth/signup")
    suspend fun register(@Body userData: HashMap<String, Any>): LogInRoot
}