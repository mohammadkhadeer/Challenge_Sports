package com.example.model.headToHeadMatches

import com.google.gson.annotations.SerializedName

data class H2HRoot  (
    @SerializedName("history")
    val history: History
)