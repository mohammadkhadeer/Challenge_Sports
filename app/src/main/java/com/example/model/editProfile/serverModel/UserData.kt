package com.example.model.editProfile.serverModel

data class UserData(


    val about: Any,
    val activeChallenge: List<Any>,
    val allow_notification: Int,
    val country: Any,
    val created_at: String,
    val created_via: String,
    val dob: Any,
    val email: String,
    val email_verified_at: Any,
    val first_name: String,
    val following: Boolean,
    val gender: String,
    val hasActiveChallenge: Boolean,
    val healthData: List<Any>,
    val height: Any,
    val id: Int,
    val image_urls: ImageUrls,
    val is_verified: Int,
    val is_web_registered: Int,
    val last_name: String,
    val name: String,
    val password: String,
    val phone_number: Any,
    val profile_img: String,
    val remember_token: Any,
    val role: Any,
    val totalFolllowingCount: Int,
    val totalFollowerCount: Int,
    val updated_at: String,
    val weight: Any
)