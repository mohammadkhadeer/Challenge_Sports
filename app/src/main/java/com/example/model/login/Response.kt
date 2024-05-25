package com.example.model.login

data class Response(
    val code: Int,
    val `data`: Data,
    val message: List<String>
)