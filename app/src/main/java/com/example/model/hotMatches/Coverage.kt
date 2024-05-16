package com.example.model.hotMatches


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Coverage (
    @SerializedName("lineup")
    var lineup: Int?,
    @SerializedName("mlive")
    var mlive: Int?
)