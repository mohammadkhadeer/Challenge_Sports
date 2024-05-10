package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class VenueDetails(
    @SerializedName("capacity")
    val capacity: Int?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("country_id")
    val countryId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: Int?
)