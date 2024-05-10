package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class Odds(
    @SerializedName("init")
    val `init`: Init?
)