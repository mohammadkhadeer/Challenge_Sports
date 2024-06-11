package com.example.presnter

import com.example.model.editProfile.EditProfileInfo

interface RecyclerViewOnclickProfile {
    fun onClick(position:Int,profile_obj: EditProfileInfo)
}