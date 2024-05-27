package com.example.apisetup.notmodel

import com.example.model.headToHeadMatches.H2HRoot
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.login.LogInRoot
import com.example.model.odds.OddsRoot
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
}