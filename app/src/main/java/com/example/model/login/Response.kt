package com.example.model.login

data class Response(
    val code: Int,
    val `data`: UserData,
    val message: List<String>
)