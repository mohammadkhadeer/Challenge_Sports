package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("message")
    val message: List<String?>?,
    @SerializedName("pagination")
    val pagination: Pagination?
)