package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class ImageUrls(
    @SerializedName("1x")
    val x1: String?,
    @SerializedName("2x")
    val x2: String?,
    @SerializedName("3x")
    val x3: String?
)