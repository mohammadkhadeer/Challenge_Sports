package com.example.presnter

import com.example.model.hotMatches.HotMatche

interface RecyclerViewOnclickMatch {
    fun onClick(position:Int,match_obj: HotMatche)
}