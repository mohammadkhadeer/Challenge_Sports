package com.example.model.odds

import com.google.gson.annotations.SerializedName


data class Oddlist (
    @SerializedName("type")
    val type: String,
    @SerializedName("odds")
    val odds: List<List<String>>
)