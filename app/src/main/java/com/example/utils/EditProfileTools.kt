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


object EditProfileTools {

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    }

    fun compressImageToJpeg(image: Bitmap, compressionQuality: Int): ByteArray {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)
        return outputStream.toByteArray()
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        var filePath: String? = null
        val uriScheme = uri.scheme

        if (uriScheme == "content") {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        } else if (uriScheme == "file") {
            filePath = uri.path
        }

        return filePath?.let { File(it) }
    }

//    fun makeMapForPhotoRequirements(imageUri: Uri,context: Context): HashMap<String, Any> {
//
//
//        val imageFileWithNames = ProfilePhoto(imageFile
//            , "profile_img"
//            ,"image/jpeg"
//            ,"profilePic@mohammad_test.jpeg")
//        val imageArray = arrayOf(imageFileWithNames)
//
//        val map = HashMap<String, Any>()
//        map["profile_img"] = imageArray
//
//        val file = File(getRealPathFromURI(context, imageUri))
//        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
//        val image = MultipartBody.Part.createFormData("profile_img", file.name, requestFile)
//        val mediaKey = RequestBody.create("text/plain".toMediaTypeOrNull(), "mediaKey")
//        val mimeType = RequestBody.create("text/plain".toMediaTypeOrNull(), "mimeType")
//        val fileName = RequestBody.create("text/plain".toMediaTypeOrNull(), "profilePic@mohammad_test.jpeg")
//
//
//        return map
//    }


    private fun getRealPathFromURI(context: Context, uri: Uri): String {
        var result: String? = null
        val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                result = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        return result ?: ""
    }

    fun makeMapForUpdateNameRequirements(value: String,key:String): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map[key] = value

        return map
    }

    fun makeMapForUpdatePasswordRequirements(oldPassword: String,newPassword:String): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["password"] = newPassword
        map["confirm_password"] = newPassword
        map["old_password"] = oldPassword

        return map
    }
    fun checkIfPasswordOrNewPasswordOrConfirmIsEmpty(passStr: String , newPassStr:String , confirmNewPassStr:String): Boolean {
        var emptyOrNot = false
        if (passStr != "" && newPassStr != ""  && confirmNewPassStr != "" && newPassStr.length >= 6)
        {
            emptyOrNot = true
        }else{
            if (passStr == "" || newPassStr == "" || confirmNewPassStr == "")
            {
                emptyOrNot = false
            }
        }
        return emptyOrNot
    }

    fun checkIfEnteredPasswordMatchWithOldPassword(newPassStr: String,confirmNewPassStr: String): Boolean {
        var matchOrNot = false

        if (newPassStr == confirmNewPassStr)
            matchOrNot = true
        else
            matchOrNot = false

        return matchOrNot
    }

    fun passOrNewPassOrConfirmNewPassIsEmptyErrorMassage(passStr: String , newPassStr:String , confirmNewPassStr:String,context: Context): String {
        var error_massage = ""
        if (passStr == "" && newPassStr == "" && confirmNewPassStr == "")
        {
            error_massage = context.getString(R.string.register_error_massage_1)
        }else{
            if (passStr == "")
            {
                error_massage = context.getString(R.string.update_password_error_massage_2)
            }

            if (newPassStr == "")
            {
                error_massage = context.getString(R.string.update_password_error_massage_3)
            }

            if (confirmNewPassStr == "" || confirmNewPassStr.length < 6)
            {
                error_massage = if (confirmNewPassStr == "") {
                    context.getString(R.string.update_password_error_massage_4)
                }else{
                    context.getString(R.string.login_error_massage_4)
                }
            }
        }
        return error_massage
    }

    fun fillProfileList(context: Context): ArrayList<EditProfileInfo>{
        var profileList: ArrayList<EditProfileInfo> = ArrayList()

        val userData = SharedPreferencesHelper.getProfileInfo(context)

        if(userData != null)
        {
            var birthday = ""
            var location = ""
            //message if dob is null
            if (userData.dob != null)
                birthday = userData.dob.toString()
            else
                birthday = context.getString(R.string.birthday_massage1)

            if (userData.country != null)
                location = userData.country.toString()
            else
                location = context.getString(R.string.location)

            profileList.add(EditProfileInfo(context.getString(R.string.full_name), userData!!.name,"name"))
            profileList.add(EditProfileInfo(context.getString(R.string.email), userData!!.email,"email_cant_be_updated"))
            profileList.add(EditProfileInfo(context.getString(R.string.birthday), birthday,"dob"))
            profileList.add(EditProfileInfo(context.getString(R.string.gender), userData!!.gender,"gender"))
            profileList.add(EditProfileInfo(context.getString(R.string.location), location,"country"))
            profileList.add(EditProfileInfo(context.getString(R.string.password), context.getString(R.string.re_password),"password"))
        }else{
            val userData = SharedPreferencesHelper.getUser(context)

            var birthday = ""
            var location = ""
            //message if dob is null
            if (userData?.dob != null)
                birthday = userData?.dob.toString()
            else
                birthday = context.getString(R.string.birthday_massage1)

            if (userData?.country != null)
                location = userData?.country.toString()
            else
                location = context.getString(R.string.location)

            profileList.add(EditProfileInfo(context.getString(R.string.full_name), userData!!.name,"name"))
            profileList.add(EditProfileInfo(context.getString(R.string.email), userData!!.email,"email_cant_be_updated"))
            profileList.add(EditProfileInfo(context.getString(R.string.birthday), birthday,"dob"))
            profileList.add(EditProfileInfo(context.getString(R.string.gender), userData!!.gender,"gender"))
            profileList.add(EditProfileInfo(context.getString(R.string.location), location,"country"))
            profileList.add(EditProfileInfo(context.getString(R.string.password), context.getString(R.string.re_password),"password"))
        }

        return profileList
    }

    fun getAProfilePic(context: Context):String{
        var profilePic: String = ""
        val userData = SharedPreferencesHelper.getProfileInfo(context)

        if(userData != null)
        {
            if (userData.profile_img != null)
                profilePic = userData.profile_img.toString()
            else
                profilePic = "https://cdn.finds.sbs//storage/placeholder.png"

        }

        return profilePic
    }


}