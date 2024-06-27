package com.example.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


object UploadTools {
    fun makeMapForUploadVideo(videoTitle: String,videoPath:Uri,context: Context,filePath:String ): Array<Any> {

        val file = File(filePath)

        val requestFile = RequestBody.create("video/*".toMediaTypeOrNull(), file)

        var body = MultipartBody.Part.createFormData("video", file.name, requestFile)
        val description = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), videoTitle)
        val title = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "empty_title")
        val type = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "3")

        val mixedArray: Array<Any> = arrayOf(body, description, title, type)

        return mixedArray
    }

    fun getRealPathFromURI(contentUri: Uri?,context: Context): String? {
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)!!
        if (cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            path = cursor.getString(column_index)
        }
        cursor.close()
        return path
    }

}
