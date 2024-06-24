package com.example.apisetup.notmodel

import com.example.model.banner.BannerRoot
import com.example.model.editProfile.serverModel.UserUpdateInfo
import com.example.model.forgotPassword.ForgotPasswordRootResponse
import com.example.model.headToHeadMatches.H2HRoot
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.login.LogInRoot
import com.example.model.news.NewsBase
import com.example.model.news.details.NewsPostBase
import com.example.model.newsVideo.VideoRoot
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsRoot
import com.example.model.updatePassword.UpdatePasswordRoot
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

    @POST(" https://sportsapi3.com/challenges/api/auth/login")
    suspend fun login(@Body userData: HashMap<String, Any>): LogInRoot

    @POST(" challenges/api/auth/signup")
    suspend fun register(@Body userData: HashMap<String, Any>): LogInRoot

    @POST(" challenges/api/forgot-password")
    suspend fun forgotPasswordRequest(@Body emailStr: HashMap<String, Any>): ForgotPasswordRootResponse

    @GET(" https://api996.com/api/v1/banner/com.challange.sports")
    suspend fun getABannerAds() : BannerRoot

    @GET(" https://app.app99877.com/api/post-list/{locale}/{page}")
    suspend fun getNews(@Path("locale") locale:String,@Path("page") page:String): NewsBase

    @GET(" https://app.app99877.com/api/post/{locale}/{postId}")
    suspend fun getNewsDetails(@Path("locale") locale:String,@Path("postId") postId:String): NewsPostBase

    @GET(" https://www.77577.live/api/video-list/{locale}/{page}")
    suspend fun getANewVideo(@Path("locale") locale:String,@Path("page") pageNumber:String): VideoRoot

    @POST(" challenges/api/user/profile")
    suspend fun updateBasicInfoRequest(@Body baseInfo: HashMap<String, Any>): UserUpdateInfo

    @POST(" challenges/api/change-password")
    suspend fun updatePassRequest(@Body baseInfo: HashMap<String, Any>): UpdatePasswordRoot
    @GET(" challenges/api/auth/user")
    suspend fun getProfileDetails(): UserUpdateInfo
}