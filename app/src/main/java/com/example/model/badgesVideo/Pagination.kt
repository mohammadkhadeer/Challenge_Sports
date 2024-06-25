package com.example.model.badgesVideo

data class Pagination(
    val current: Int,
    val first: Int,
    val from: Int,
    val last: Int,
    val next: Int,
    val pages: List<Int>,
    val previous: Int,
    val to: Int,
    val total: Int
)