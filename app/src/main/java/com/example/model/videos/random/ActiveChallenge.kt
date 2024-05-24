package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class ActiveChallenge(
    @SerializedName("active")
    val active: Int?,
    @SerializedName("challenges_id")
    val challengesId: Int?,
    @SerializedName("completed_at")
    val completedAt: Any?,
    @SerializedName("created_at")
    val createdAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_completed")
    val isCompleted: Int?,
    @SerializedName("left_at")
    val leftAt: Any?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?,
    @SerializedName("user_id")
    val userId: Int?
)