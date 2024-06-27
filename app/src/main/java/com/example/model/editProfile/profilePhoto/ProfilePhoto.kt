package com.example.model.editProfile.profilePhoto

import android.graphics.Bitmap
import okhttp3.RequestBody


data class ProfilePhoto(

    val data: RequestBody,
    val mediaKey: String,
    val mimeType: String,
    val fileName: String

)
