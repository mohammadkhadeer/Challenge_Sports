package com.example.view.allNewsVideo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.newsVideo.VideoList
import com.example.model.newsVideo.VideoRoot
import com.example.presnter.NewsVideoRecyclerViewOnclick
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.MySharableObject
import com.example.view.mainActivity.homeAdapter.newsAdapter.AllNewsAdapter
import com.example.view.videoDetails.VideoDetailsActivity
import com.example.view.videoDetails.suggestionVideos.VideoNewsAdapterV
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class AllNewsVideoActivity : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var recycler_view: RecyclerView
    private lateinit var pro_bar_load_more: ProgressBar

    //value
    var pageNumber: Int = 1
    private lateinit var adapterAll: VideoNewsAdapterV
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var videoList:  List<VideoList>? = null

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_news_video)

        statusBarColor()
        casting()
        actionListenerToBack()
        checkIfTheRecyclerViewReachBottom()

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(this)

        if (MySharableObject.videoListObject != null){
            createAList(MySharableObject.videoListObject!!)
        }else{
            var lang = SharedPreferencesHelper.getALanguage(this)!!
            if (lang == "zh")
                lang = "cn"
            view_model.getANewVideo(lang,"1")
        }

        observeResponse()
    }

    private fun observeResponse() {
        view_model.newsVideoData.observe(this){
            if (it.status== Status.SUCCESS){
                updateAList(it.data!!)
            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun updateAList(data: VideoRoot) {
        pro_bar_load_more.isVisible = false
        // Scroll up by 100 pixels
        recycler_view.scrollBy(0, 100)
        adapterAll.updateList(data.list)
    }

    private fun createAList(videoListObject: List<VideoList>) {

        videoList = videoListObject
        recycler_view.isNestedScrollingEnabled = false

        adapterAll = VideoNewsAdapterV(this, videoList as ArrayList<VideoList>,
            object : NewsVideoRecyclerViewOnclick {
                override fun onClick(obj: VideoList) {
                    moveToVideoDetailsScreen(obj)
                }
            })

        recycler_view.adapter = adapterAll

        recycler_view.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun moveToVideoDetailsScreen(obj: VideoList) {
        videoList!!.filter {  it.id != obj.id }

        MySharableObject.videoListObject = videoList
        MySharableObject.videoNewsObject = obj
        val intent = Intent(this, VideoDetailsActivity::class.java)
        startActivity(intent)
    }

    private fun checkIfTheRecyclerViewReachBottom() {
        // Add scroll listener to detect when the RecyclerView reaches the bottom
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    // RecyclerView can't scroll further down, hence at the bottom
                    onReachedBottom()
                }
            }
        })
    }

    private fun onReachedBottom() {
        // Your code when RecyclerView reaches the bottom
        pro_bar_load_more.isVisible = true
        pageNumber +=1

        var lang = SharedPreferencesHelper.getALanguage(this)!!
        if (lang == "zh")
            lang = "cn"

        view_model.getANewVideo(lang,pageNumber.toString())
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            finish()
        }
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun casting() {
        back_image = findViewById<ImageView>(R.id.back_image)
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        pro_bar_load_more = findViewById<ProgressBar>(R.id.pro_bar_load_more)
    }
}