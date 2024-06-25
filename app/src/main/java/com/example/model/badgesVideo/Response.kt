package com.example.model.badgesVideo

data class Response(
    val code: Int,
    val `data`: List<Data>,
    val message: List<String>,
    val pagination: Pagination
)