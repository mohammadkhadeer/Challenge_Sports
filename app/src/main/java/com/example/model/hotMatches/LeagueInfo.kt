package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class LeagueInfo(
    @SerializedName("cn_name")
    val cnName: String?,
    @SerializedName("en_name")
    val enName: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("primary_color")
    val primaryColor: String?,
    @SerializedName("secondary_color")
    val secondaryColor: String?,
    @SerializedName("short_name")
    val shortName: String?
)