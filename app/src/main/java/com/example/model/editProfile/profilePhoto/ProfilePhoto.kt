package com.example.model.editProfile.profilePhoto

import android.graphics.Bitmap


data class ProfilePhoto(

    val data: Bitmap,
    val mediaKey: String,
    val mimeType: String,
    val fileName: String

)
