package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apisetup.notmodel.ApiHelper
import androidx.lifecycle.viewModelScope
import com.example.apisetup.notmodel.Resource

import com.example.model.hotMatches.HotMatchBaseClass

import com.example.apisetup.notmodel.Resource.Companion.loading
import com.example.model.badgesVideo.BadgesVideosRoot
import com.example.model.banner.BannerRoot
import com.example.model.editProfile.profilePhoto.ProfilePhoto
import com.example.model.editProfile.serverModel.UserUpdateInfo
import com.example.model.forgotPassword.ForgotPasswordRootResponse
import com.example.model.headToHeadMatches.H2HRoot
import com.example.model.login.LogInRoot
import com.example.model.news.NewsBase
import com.example.model.news.details.NewsPostBase
import com.example.model.newsVideo.VideoRoot
import com.example.model.odds.OddsRoot
import com.example.model.updatePassword.UpdatePasswordRoot
import com.example.model.uploadVideo.UploadVideoResponse
import com.example.model.userVideos.UserVideosRoot
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.sql.DriverManager.println

class MyViewModel(private val apiHelper: ApiHelper) : ViewModel(){
    var matches_root = MutableLiveData<Resource<HotMatchBaseClass>>()
    var matches_upcoming = MutableLiveData<Resource<HotMatchBaseClass>>()
    var matches_finished = MutableLiveData<Resource<HotMatchBaseClass>>()
    var matches_live = MutableLiveData<Resource<HotMatchBaseClass>>()

    //match details
    var matches_odds = MutableLiveData<Resource< List<OddsRoot> >>()
    var h2hList = MutableLiveData<Resource< H2HRoot >>()

    //login and register same value to handle both
    var login = MutableLiveData<Resource< LogInRoot >>()

    //forgot password
    var forgot_password = MutableLiveData<Resource< ForgotPasswordRootResponse >>()

    //banner Ads
    var bannerRoot = MutableLiveData<Resource< BannerRoot >>()

    //news
    var newsLiveData = MutableLiveData<Resource<NewsBase>>()
    var newsDetailsData = MutableLiveData<Resource<NewsPostBase>>()

    //video
    var newsVideoData = MutableLiveData<Resource<VideoRoot>>()

    //userUpdateInfo
    var updateBasicInfo = MutableLiveData<Resource<UserUpdateInfo>>()

    //userUpdateInfo,gender
    var updateGenderInfo = MutableLiveData<Resource<UserUpdateInfo>>()

    //userUpdateInfo //photo
    var updatePhoto = MutableLiveData<Resource<UserUpdateInfo>>()

    //updatePassword
    var updatePass = MutableLiveData<Resource<UpdatePasswordRoot>>()

    //userUpdateInfo
    var basicProfile = MutableLiveData<Resource<UserUpdateInfo>>()

    //user videos , BookMarkVideos
    var userVideo = MutableLiveData<Resource<UserVideosRoot>>()

    //user videos , UserVideos
    var userVideo2 = MutableLiveData<Resource<UserVideosRoot>>()

    //user videos , LikeVideos
    var userVideo3 = MutableLiveData<Resource<UserVideosRoot>>()

    //user videos , BadgesVideos
    var userVideo4 = MutableLiveData<Resource<BadgesVideosRoot>>()

    //upload videos
    var uploadVideo = MutableLiveData<Resource<UploadVideoResponse>>()

    fun getHotMatches(){
        viewModelScope.launch {
            matches_root.postValue(Resource.loading(null))
            try {
                val myData=apiHelper.getHotMatches(true)
                //println(myData)
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
                h2hList.postValue(Resource.success(myData))
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    fun login(userData:HashMap<String, Any>){
        viewModelScope.launch {
            login.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.login(userData)
                //println(myData)
                login.postValue(Resource.success(myData))

            }catch (e:Exception){
                login.postValue(Resource.error(e.message.toString(),null))
                println(e.message)
            }
        }
    }

    fun register(userData:HashMap<String, Any>){
        viewModelScope.launch {
            login.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.register(userData)
                //println(myData)
                login.postValue(Resource.success(myData))

            }catch (e:Exception){
                login.postValue(Resource.error(e.message.toString(),null))
                println(e.message)
            }
        }
    }

    fun forgotPasswordRequest(emailStr:HashMap<String, Any>){
        viewModelScope.launch {
            forgot_password.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.forgotPasswordRequest(emailStr)
                //println(myData)
                Log.i("TAG","myData "+myData)
                forgot_password.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                forgot_password.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getABannerAds(){
        viewModelScope.launch {
            bannerRoot.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getBannerAds()
                //println(myData)
                bannerRoot.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                bannerRoot.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getNews(pageNumber: String,lang:String) {
        viewModelScope.launch {
            newsLiveData.postValue(Resource.loading(null))
            try {
                val news = apiHelper.getNews(lang, pageNumber)
                newsLiveData.postValue(Resource.success(news))
            } catch (e: Exception) {
                newsLiveData.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getANewsDetails(postID: String,lang: String) {
        viewModelScope.launch {
            newsDetailsData.postValue(Resource.loading(null))
            try {
                val news = apiHelper.getNewsDetails(lang, postID)
                newsDetailsData.postValue(Resource.success(news))
            } catch (e: Exception) {
                newsDetailsData.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getANewVideo(lang: String,pageNumber: String) {
        viewModelScope.launch {
            newsVideoData.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getANewVideo(lang, pageNumber)
//                println(myData)
                newsVideoData.postValue(Resource.success(myData))
            } catch (e: Exception) {
                println(e.toString())
                newsVideoData.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun updateBasicGenderInfoRequest(basicInfo: HashMap<String, Any>) {
        viewModelScope.launch {
            updateGenderInfo.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.updateBasicInfo(basicInfo)
                println(myData)
                updateGenderInfo.postValue(Resource.success(myData))

            } catch (e: Exception) {
                println(e.message)
                updateGenderInfo.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }

    fun updateBasicInfoRequest(basicInfo:HashMap<String, Any>){
        viewModelScope.launch {
            updateBasicInfo.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.updateBasicInfo(basicInfo)
                println(myData)
                updateBasicInfo.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                updateBasicInfo.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun updatePassRequest(passInfo:HashMap<String, Any>){
        viewModelScope.launch {
            updatePass.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.updatePassword(passInfo)
//                println(myData)
                updatePass.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                updatePass.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getBasicInfoRequest(){
        viewModelScope.launch {
            basicProfile.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getBasicProfileInfo()
//                println(myData)
                basicProfile.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                basicProfile.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun updatePhoto(image: MultipartBody.Part, mediaKey: RequestBody, mimeType: RequestBody, fileName: RequestBody){
        viewModelScope.launch {
            updatePhoto.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.uploadPhoto(image,mediaKey,mimeType,fileName)
                println(myData)
                updatePhoto.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                updatePhoto.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getABookMarkVideos(){
        viewModelScope.launch {
            userVideo.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getAUserBookMarkVideos()
//                println(myData)
                userVideo.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                userVideo.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getAVideos(){
        viewModelScope.launch {
            userVideo2.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getAUserVideos()
                println(myData)
                userVideo2.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                userVideo2.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getALikedVideos(){
        viewModelScope.launch {
            userVideo3.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getALikeVideos()
//                println(myData)
                userVideo3.postValue(Resource.success(myData))

            }catch (e:Exception){
//                println(e.message)
                userVideo3.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun getABadgesVideos(){
        viewModelScope.launch {
            userVideo4.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.getBadgesVideos()
//                println(myData)
                userVideo4.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                userVideo4.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }

    fun uploadVideo(body: MultipartBody.Part
                    , description: RequestBody
                    , title: RequestBody
                    , type: RequestBody
    ){
        viewModelScope.launch {
            uploadVideo.postValue(Resource.loading(null))
            try {
                val myData = apiHelper.uploadVideo(body,description,title,type)
                println(myData)
                uploadVideo.postValue(Resource.success(myData))

            }catch (e:Exception){
                println(e.message)
                uploadVideo.postValue(Resource.error(e.message.toString(),null))
            }
        }
    }
}