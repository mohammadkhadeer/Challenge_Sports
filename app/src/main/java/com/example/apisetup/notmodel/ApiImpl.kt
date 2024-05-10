package com.example.apisetup.notmodel

import com.example.model.hotMatches.HotMatchBaseClass

class ApiImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getHotMatches(hasHot: Boolean): HotMatchBaseClass = apiService.getHotMatches(hasHot)
}