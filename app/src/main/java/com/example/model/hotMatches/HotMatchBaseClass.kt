package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class HotMatchBaseClass(
    @SerializedName("hotLeagues")
    val hotLeagues: List<HotLeague?>?,
    @SerializedName("hotMatches")
    val hotMatches: List<HotMatche?>?,
    @SerializedName("matchList")
    val matchList: List<HotMatche?>?
)