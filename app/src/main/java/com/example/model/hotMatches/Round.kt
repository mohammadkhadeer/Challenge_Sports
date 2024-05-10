package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class Round(
    @SerializedName("group_num")
    val groupNum: Int?,
    @SerializedName("round_num")
    val roundNum: Int?,
    @SerializedName("stage_id")
    val stageId: String?
)