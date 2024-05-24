package com.example.model.videos.random


import com.google.gson.annotations.SerializedName

data class TriedByUser(
    @SerializedName("about")
    val about: String?,
    @SerializedName("activeChallenge")
    val activeChallenge: List<ActiveChallenge?>?,
    @SerializedName("allow_notification")
    val allowNotification: Int?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("following")
    val following: Boolean?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("hasActiveChallenge")
    val hasActiveChallenge: Boolean?,
    @SerializedName("healthData")
    val healthData: List<HealthData?>?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_urls")
    val imageUrls: ImageUrls?,
    @SerializedName("is_verified")
    val isVerified: Int?,
    @SerializedName("is_web_registered")
    val isWebRegistered: Int?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("profile_img")
    val profileImg: String?,
    @SerializedName("remember_token")
    val rememberToken: Any?,
    @SerializedName("role")
    val role: Any?,
    @SerializedName("totalFolllowingCount")
    val totalFolllowingCount: Int?,
    @SerializedName("totalFollowerCount")
    val totalFollowerCount: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("weight")
    val weight: Int?
)