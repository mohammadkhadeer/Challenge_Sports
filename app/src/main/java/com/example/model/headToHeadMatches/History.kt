package com.example.model.headToHeadMatches

import com.google.gson.annotations.SerializedName

data class History (
    @SerializedName("home_match_info")
    val homeMatchInfo: List<MatchInfo>,
    @SerializedName("away_match_info")
    val awayMatchInfo: List<MatchInfo>
)