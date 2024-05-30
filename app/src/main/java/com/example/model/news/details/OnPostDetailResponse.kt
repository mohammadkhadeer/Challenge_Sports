package com.example.model.news.details

interface OnPostDetailResponse<T> {
    fun onSuccess(responseBody:T)
    fun onFailure(message:String)
    fun onLoading(message:String)
}