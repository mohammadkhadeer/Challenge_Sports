package com.example.model.odds

import com.google.gson.annotations.SerializedName

class RootObject (
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("oddlist")
    val oddlist: List<Oddlist>
)