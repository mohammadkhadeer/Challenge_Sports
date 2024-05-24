package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class HealthData(
    @SerializedName("challenges_id")
    val challengesId: Int?,
    @SerializedName("total_value")
    val totalValue: Double?,
    @SerializedName("type_id")
    val typeId: Int?,
    @SerializedName("type_name")
    val typeName: String?,
    @SerializedName("type_slug")
    val typeSlug: String?
)