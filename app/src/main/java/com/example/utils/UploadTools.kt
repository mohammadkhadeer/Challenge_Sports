package com.example.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import com.example.apisetup.BuildConfig
import com.example.apisetup.R
import com.example.model.editProfile.EditProfileInfo
import com.example.model.editProfile.profilePhoto.ProfilePhoto
import com.example.model.headToHeadMatches.History
import com.example.model.headToHeadMatches.MatchInfo
import com.example.model.hotMatches.MatchStatusJ
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsCompanyComp
import com.example.model.odds.OddsRoot
import com.example.sharedPreferences.SharedPreferencesHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


object UploadTools {
    fun makeMapForUploadVideo(videoTitle: String,videoPath:Uri,context: Context): Array<Any> {

        val filePath = getRealPathFromURI(videoPath,context)
        val file = File(filePath)

        val requestFile = RequestBody.create("video/*".toMediaTypeOrNull(), file)
        var body = MultipartBody.Part.createFormData("video", file.name, requestFile)
        val description = RequestBody.create("description".toMediaTypeOrNull(), videoTitle)
        val title = RequestBody.create("title".toMediaTypeOrNull(), "empty_title")
        val type = RequestBody.create("type".toMediaTypeOrNull(), "3")

        val mixedArray: Array<Any> = arrayOf(body, description, title, type)

        return mixedArray
    }

}

 fun getRealPathFromURI(contentUri: Uri,context: Context): String {
    var result: String
    val cursor = context.contentResolver.query(contentUri, null, null, null, null)
    if (cursor == null) {
        result = contentUri.path!!
    } else {
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}