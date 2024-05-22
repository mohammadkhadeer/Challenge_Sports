package com.example.apisetup.notmodel

import com.example.model.headToHeadMatches.H2HRoot
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.odds.OddsRoot

interface ApiHelper {
    suspend fun getHotMatches(hasHot:Boolean) : HotMatchBaseClass
    suspend fun getUpcomingMatches() : HotMatchBaseClass
    suspend fun getFinishedMatches() : HotMatchBaseClass
    suspend fun getLiveMatches() : HotMatchBaseClass
    suspend fun getMatchOdds(match_id:String) : List<OddsRoot>
    suspend fun getH2HListMatches(match_id:String) : H2HRoot
}