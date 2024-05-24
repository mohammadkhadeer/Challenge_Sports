package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("challenged_video_id")
    val challengedVideoId: Any?,
    @SerializedName("commentCount")
    val commentCount: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("downvoteCount")
    val downvoteCount: Int?,
    @SerializedName("hasUpvoted")
    val hasUpvoted: Boolean?,
    @SerializedName("hasdownvoted")
    val hasdownvoted: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_approved")
    val isApproved: Int?,
    @SerializedName("is_bookmarked")
    val isBookmarked: Boolean?,
    @SerializedName("is_featured")
    val isFeatured: Boolean?,
    @SerializedName("priority")
    val priority: Int?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("tried_by_user")
    val triedByUser: TriedByUser?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("uploaded_at")
    val uploadedAt: String?,
    @SerializedName("upvoteCount")
    val upvoteCount: Int?,
    @SerializedName("video_url")
    val videoUrl: String?
)