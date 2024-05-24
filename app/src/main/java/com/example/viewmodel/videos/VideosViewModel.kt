package com.example.viewmodel.videos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apisetup.notmodel.APIAccessSlug
import com.example.apisetup.notmodel.ApiHelper
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.model.videos.random.Data
import com.example.model.videos.random.RandomVidsBase
import kotlinx.coroutines.launch

class VideosViewModel(val apiHelper: ApiHelper) :ViewModel() {
    var videoRandom = MutableLiveData<Resource<List<Data>>>()

    fun getRandomVids(){
        viewModelScope.launch {
            videoRandom.postValue(Resource.loading(null))
            try {
                val response = apiHelper.getVideos("3")

                videoRandom.postValue(Resource.success(response.response?.data) as Resource<List<Data>>?)

            }catch (e:Exception){
                videoRandom.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }
}