package com.example.model.editProfile.serverModel

data class UserResponse(
    val code: Int,
    val `data`: UserData,
    val message: List<String>
)