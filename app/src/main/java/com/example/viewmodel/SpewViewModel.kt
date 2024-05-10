package com.example.viewmodel

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.apisetup.notmodel.ApiImpl
import com.example.apisetup.notmodel.RetorfitBuilder

object SpewViewModel {
    fun giveMeViewModel(context: ComponentActivity): MyViewModel {

        return ViewModelProvider(context.viewModelStore
            ,ViewModelFactory(ApiImpl(RetorfitBuilder.apiService))).get()

    }

}