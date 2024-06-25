package com.example.apisetup.notmodel

import com.example.model.badgesVideo.BadgesVideosRoot
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
import com.example.model.updatePassword.UpdatePasswordRoot
import com.example.model.userVideos.UserVideosRoot
import com.example.model.videos.random.RandomVidsBase

class ApiImpl(private val apiService: ApiService) : ApiHelper {
    //matches
    override suspend fun getHotMatches(hasHot: Boolean): HotMatchBaseClass = apiService.getHotMatches(hasHot)
    override suspend fun getUpcomingMatches(): HotMatchBaseClass = apiService.getUpcomingMatches()
    override suspend fun getFinishedMatches(): HotMatchBaseClass = apiService.getFinishedMatches()
    override suspend fun getLiveMatches(): HotMatchBaseClass = apiService.getLiveMatches()

    //match details
    override suspend fun getMatchOdds(match_id: String): List<OddsRoot> = apiService.getMatchOddList(match_id)
    override suspend fun getH2HListMatches(match_id: String): H2HRoot  = apiService.getH2HListMatches(match_id)
    override suspend fun getVideos(type: String): RandomVidsBase = apiService.getVideos(type)
    override suspend fun login(userData: HashMap<String, Any>): LogInRoot = apiService.login(userData)
    override suspend fun register(userData: HashMap<String, Any>): LogInRoot = apiService.register(userData)
    override suspend fun forgotPasswordRequest(emailStr_with_a_key: HashMap<String, Any>): ForgotPasswordRootResponse = apiService.forgotPasswordRequest(emailStr_with_a_key)
    override suspend fun getBannerAds(): BannerRoot = apiService.getABannerAds()
    override suspend fun getNews(locale: String, page: String): NewsBase = apiService.getNews(locale,page)
    override suspend fun getNewsDetails(locale: String, id: String): NewsPostBase = apiService.getNewsDetails(locale,id)
    override suspend fun getANewVideo(locale: String, pageNumber: String): VideoRoot = apiService.getANewVideo(locale,pageNumber)
    override suspend fun updateBasicInfo(basic_info: HashMap<String, Any>): UserUpdateInfo = apiService.updateBasicInfoRequest(basic_info)
    override suspend fun updatePassword(passInfo: HashMap<String, Any>): UpdatePasswordRoot = apiService.updatePassRequest(passInfo)
    override suspend fun getBasicProfileInfo(): UserUpdateInfo = apiService.getProfileDetails()
    override suspend fun getAUserBookMarkVideos(): UserVideosRoot = apiService.getAUserBookMarkVideos()
    override suspend fun getAUserVideos(): UserVideosRoot = apiService.getAUserVideos()
    override suspend fun getALikeVideos(): UserVideosRoot = apiService.getALikedVideos()
    override suspend fun getBadgesVideos(): BadgesVideosRoot = apiService.getABadgesVideos()
}