package com.example.model.uploadVideo

data class Response(
    val code: Int,
    val `data`: List<Any>,
    val message: List<String>
)