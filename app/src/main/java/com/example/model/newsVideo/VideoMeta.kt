package com.example.model.newsVideo

import com.google.gson.annotations.SerializedName

class VideoMeta (
    @SerializedName("locale")
    val locale: String,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("lastPage")
    val lastPage: Int,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("total")
    val total: Int
)