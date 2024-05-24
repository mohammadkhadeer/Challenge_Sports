package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("current")
    val current: Int?,
    @SerializedName("first")
    val first: Int?,
    @SerializedName("from")
    val from: Int?,
    @SerializedName("last")
    val last: Int?,
    @SerializedName("next")
    val next: Int?,
    @SerializedName("pages")
    val pages: List<Int?>?,
    @SerializedName("previous")
    val previous: Int?,
    @SerializedName("to")
    val to: Int?,
    @SerializedName("total")
    val total: Int?
)