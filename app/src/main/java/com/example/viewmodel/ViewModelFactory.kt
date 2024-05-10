package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apisetup.notmodel.ApiHelper

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       /* if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiHelper) as T//TODO: Replace with Viewmodel
        } */
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(apiHelper) as T//TODO: Replace with Viewmodel
        }
        throw IllegalArgumentException("Unknown class name")
    }

}