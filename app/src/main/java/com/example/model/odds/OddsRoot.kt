package com.example.model.odds

//
//  RootClass.kt
//
//  Generated using https://jsonmaster.github.io
//  Created on May 17, 2024
//

import com.google.gson.annotations.SerializedName

data class OddsRoot (
    @SerializedName("company_id")
    val companyId: Int,
    @SerializedName("oddlist")
    val oddlist: List<Oddlist>
)

