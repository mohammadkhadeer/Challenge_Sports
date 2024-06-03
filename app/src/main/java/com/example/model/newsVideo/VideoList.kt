package com.example.model.newsVideo

import com.google.gson.annotations.SerializedName

class VideoList (
    @SerializedName("id")
    val id: Int,
    @SerializedName("cf_hls_url")
    val cfHlsUrl: String,
    @SerializedName("video")
    val video: String,
    @SerializedName("create_time")
    val createTime: String,
    @SerializedName("thumbnail_path")
    val thumbnailPath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("path")
    val path: String
)