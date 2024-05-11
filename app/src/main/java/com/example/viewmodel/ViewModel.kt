package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apisetup.notmodel.ApiHelper
import androidx.lifecycle.viewModelScope
import com.example.apisetup.notmodel.Resource

import com.example.model.hotMatches.HotMatchBaseClass

import com.example.apisetup.notmodel.Resource.Companion.loading
import kotlinx.coroutines.launch
import java.sql.DriverManager.println

class MyViewModel(private val apiHelper: ApiHelper) : ViewModel(){
    var matches_root = MutableLiveData<Resource<HotMatchBaseClass>>()

    init {

 }
     fun makeCallAPI(){
        viewModelScope.launch {
            matches_root.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getHotMatches(true)
                //println(myData)
                Log.i("TAG","myData "+myData)
                matches_root.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }
}