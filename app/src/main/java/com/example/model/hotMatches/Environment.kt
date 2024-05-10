package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class Environment(
    @SerializedName("humidity")
    val humidity: String?,
    @SerializedName("pressure")
    val pressure: String?,
    @SerializedName("temperature")
    val temperature: String?,
    @SerializedName("weather")
    val weather: Int?,
    @SerializedName("wind")
    val wind: String?
)