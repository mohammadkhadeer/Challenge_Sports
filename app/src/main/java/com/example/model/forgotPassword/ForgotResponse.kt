package com.example.model.forgotPassword

data class ForgotResponse(
    val code: Int,
    val `data`: ForgotPasswordData,
    val message: List<String>
)