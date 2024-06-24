package com.example.model.userVideos

data class Response(
    val code: Int,
    val `data`: List<Data>,
    val message: List<String>
)