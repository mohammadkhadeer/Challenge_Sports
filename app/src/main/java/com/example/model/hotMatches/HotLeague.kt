package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class HotLeague(
    @SerializedName("id")
    val id: String?,
    @SerializedName("nameCn")
    val nameCn: String?,
    @SerializedName("nameEn")
    val nameEn: String?,
    @SerializedName("nameEnShort")
    val nameEnShort: String?
)