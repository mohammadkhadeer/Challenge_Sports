package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apisetup.notmodel.ApiHelper
import androidx.lifecycle.viewModelScope
import com.example.apisetup.notmodel.Resource

import com.example.model.hotMatches.HotMatchBaseClass

import com.example.apisetup.notmodel.Resource.Companion.loading
import com.example.model.headToHeadMatches.H2HRoot
import com.example.model.odds.OddsRoot
import kotlinx.coroutines.launch
import java.sql.DriverManager.println

class MyViewModel(private val apiHelper: ApiHelper) : ViewModel(){
    var matches_root = MutableLiveData<Resource<HotMatchBaseClass>>()
    var matches_upcoming = MutableLiveData<Resource<HotMatchBaseClass>>()
    var matches_finished = MutableLiveData<Resource<HotMatchBaseClass>>()
    var matches_live = MutableLiveData<Resource<HotMatchBaseClass>>()

    //match details
    var matches_odds = MutableLiveData<Resource< List<OddsRoot> >>()
    var h2hList = MutableLiveData<Resource< H2HRoot >>()

    init {

 }
     fun getHotMatches(){
        viewModelScope.launch {
            matches_root.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getHotMatches(true)
                //println(myData)
//                Log.i("TAG","myData "+myData)
                matches_root.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    fun getUpcomingMatches(){
        viewModelScope.launch {
            matches_upcoming.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getUpcomingMatches()
                //println(myData)
//                Log.i("TAG","myData "+myData)
                matches_upcoming.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }


    fun getFinishedMatches(){
        viewModelScope.launch {
            matches_finished.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getFinishedMatches()
                //println(myData)
//                Log.i("TAG","myData "+myData)
                matches_finished.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    fun getLiveMatches(){
        viewModelScope.launch {
            matches_live.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getLiveMatches()
                //println(myData)
//                Log.i("TAG","myData "+myData)
                matches_live.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }



    fun getMatchOdds(match_id:String){
        viewModelScope.launch {
            matches_odds.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getMatchOdds(match_id)
                //println(myData)
//                Log.i("TAG","myData "+myData)
                matches_odds.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    fun getH2HListMatches(match_id:String){
        viewModelScope.launch {
            h2hList.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getH2HListMatches(match_id)
                //println(myData)
                Log.i("TAG","myData "+myData)
                h2hList.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }


}