package com.example.viewmodel

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.apisetup.notmodel.APIAccessSlug
import com.example.apisetup.notmodel.ApiImpl
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.viewmodel.videos.VideosViewModel

object SpewViewModel {
    fun giveMeViewModel(context: ComponentActivity): MyViewModel {

        return ViewModelProvider(context.viewModelStore
            ,ViewModelFactory(ApiImpl(RetorfitBuilder.apiService))).get()

    }

    fun giveViewModelVideos(context: ComponentActivity):VideosViewModel{
        RetorfitBuilder.changeAPIAccess(APIAccessSlug.CHALLENGES_API)
        return ViewModelProvider(context.viewModelStore,
            ViewModelFactory(
                ApiImpl(
                    RetorfitBuilder.apiService),APIAccessSlug.CHALLENGES_API)
        ).get()
    }




}