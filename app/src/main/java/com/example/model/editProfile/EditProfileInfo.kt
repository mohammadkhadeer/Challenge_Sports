package com.example.model.editProfile

data class EditProfileInfo(

    val title: String,
    val contentTxt: String,
    val value_in_server: String
    //i use this to can pass it to same fun where if i have
    //to update value in retrofit2 i have to set it in map<>

)