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
import com.example.model.odds.OddsRoot
import com.example.model.videos.random.RandomVidsBase

interface ApiHelper {
    suspend fun getHotMatches(hasHot:Boolean) : HotMatchBaseClass
    suspend fun getUpcomingMatches() : HotMatchBaseClass
    suspend fun getFinishedMatches() : HotMatchBaseClass
    suspend fun getLiveMatches() : HotMatchBaseClass
    suspend fun getMatchOdds(match_id:String) : List<OddsRoot>
    suspend fun getH2HListMatches(match_id:String) : H2HRoot

    suspend fun getVideos(type:String) : RandomVidsBase
    suspend fun login(userData:HashMap<String, Any>) : LogInRoot
    suspend fun register(userData:HashMap<String, Any>) : LogInRoot
    suspend fun forgotPasswordRequest(emailStr_with_a_key:HashMap<String, Any>) : ForgotPasswordRootResponse
    suspend fun getBannerAds() : BannerRoot
    suspend fun getNews(locale: String, page: String) : NewsBase
    suspend fun getNewsDetails(locale: String, id: String) : NewsPostBase
    suspend fun getANewVideo(locale: String, pageNumber: String) : VideoRoot
    suspend fun updateBasicInfo(basic_info:HashMap<String, Any>) : UserUpdateInfo
}