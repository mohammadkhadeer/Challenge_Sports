package com.example.apisetup.notmodel

import com.example.model.hotMatches.HotMatchBaseClass

interface ApiHelper {
    suspend fun getHotMatches(hasHot:Boolean) : HotMatchBaseClass
}