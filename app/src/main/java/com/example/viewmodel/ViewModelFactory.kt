package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apisetup.notmodel.APIAccessSlug
import com.example.apisetup.notmodel.ApiHelper
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.viewmodel.videos.VideosViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

        var slug= APIAccessSlug.SPORTS_API
        constructor(apiHelper: ApiHelper, apiAccessSlug: APIAccessSlug) : this(apiHelper){
            slug=apiAccessSlug
        }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //RetorfitBuilder.changeAPIAccess(slug)
        if (modelClass.isAssignableFrom(VideosViewModel::class.java) && slug == APIAccessSlug.CHALLENGES_API) {
            return VideosViewModel(apiHelper) as T
        }
        if (modelClass.isAssignableFrom(MyViewModel::class.java) && slug == APIAccessSlug.SPORTS_API) {
            return MyViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}