package com.example.apisetup.notmodel

import com.example.model.hotMatches.HotMatchBaseClass

class ApiImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getHotMatches(hasHot: Boolean): HotMatchBaseClass = apiService.getHotMatches(hasHot)
    override suspend fun getUpcomingMatches(): HotMatchBaseClass = apiService.getUpcomingMatches()
    override suspend fun getFinishedMatches(): HotMatchBaseClass = apiService.getFinishedMatches()
    override suspend fun getLiveMatches(): HotMatchBaseClass = apiService.getLiveMatches()

}