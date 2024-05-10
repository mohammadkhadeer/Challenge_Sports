package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class Euro(
    @SerializedName("away")
    val away: Double?,
    @SerializedName("handicap")
    val handicap: Double?,
    @SerializedName("home")
    val home: Double?
)