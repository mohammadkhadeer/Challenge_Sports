package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.apisetup.notmodel.ApiHelper
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.sql.DriverManager.println

class MyViewModel(private val apiHelper: ApiHelper) : ViewModel(){
 init {

 }
     fun makeCallAPI(){
        viewModelScope.launch {
            try {
                val myData=apiHelper.getHotMatches(true)
                //println(myData)
                Log.i("TAG","myData "+myData)
            }catch (e:Exception){
                println(e.message)
            }
        }
    }
}