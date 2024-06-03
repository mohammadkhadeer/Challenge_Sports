package com.example.model.newsVideo

import com.google.gson.annotations.SerializedName

class VideoRoot (
    @SerializedName("list")
    val list: List<VideoList>,
    @SerializedName("meta")
    val meta: VideoMeta
)