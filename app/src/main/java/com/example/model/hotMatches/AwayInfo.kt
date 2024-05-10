package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class AwayInfo(
    @SerializedName("away_score")
    val awayScore: Int?,
    @SerializedName("cn_name")
    val cnName: String?,
    @SerializedName("corner_score")
    val cornerScore: Int?,
    @SerializedName("en_name")
    val enName: String?,
    @SerializedName("en_short_name")
    val enShortName: String?,
    @SerializedName("half_time_score")
    val halfTimeScore: Int?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("overtime_score")
    val overtimeScore: Int?,
    @SerializedName("penalty_score")
    val penaltyScore: Int?,
    @SerializedName("red_cards")
    val redCards: Int?,
    @SerializedName("yellow_cards")
    val yellowCards: Int?
)