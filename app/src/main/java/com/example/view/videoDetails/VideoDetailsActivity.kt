package com.example.view.videoDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.newsVideo.VideoList
import com.example.model.newsVideo.VideoRoot
import com.example.presnter.NewsVideoRecyclerViewOnclick
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.MySharableObject
import com.example.view.mainActivity.homeAdapter.VideoAdapter.VideoNewsAdapterHorizontal
import com.example.view.videoDetails.suggestionVideos.VideoNewsAdapterV
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import java.util.function.Predicate

class VideoDetailsActivity : AppCompatActivity() {
    //ui
    private lateinit var videoPlayer: StyledPlayerView
    private lateinit var back_image: ImageView
    private lateinit var recycler_view: RecyclerView
    private lateinit var text_view: TextView
    private lateinit var text_view_time: TextView
    private lateinit var pro_bar_load_more: ProgressBar

    //value
    private var exo:ExoPlayer?=null
    private var player:ExoPlayer?=null
    private var videoList:  List<VideoList>? = null
    var pageNumber: Int = 1
    private lateinit var adapterAll: VideoNewsAdapterV

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)

        statusBarColor()
        casting()
        actionListenerToBack()
        checkIfTheRecyclerViewReachBottom()

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(this)

        if (MySharableObject.videoNewsObject != null)
        {
            fillABasicInfo()
            if (MySharableObject.videoListObject != null){
                createASuggestionList()
            }else{
                var lang = SharedPreferencesHelper.getALanguage(this)!!
                if (lang == "zh")
                    lang = "cn"

                view_model.getANewVideo(lang,"1")
            }
        }

        observeResponse()
    }

    private fun updateAList(data: VideoRoot) {
        pro_bar_load_more.isVisible = false
        // Scroll up by 100 pixels
        recycler_view.scrollBy(0, 100)
        adapterAll.updateList(data.list)
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

    private fun createASuggestionList() {
        videoList = MySharableObject.videoListObject
        recycler_view.isNestedScrollingEnabled = false;

        adapterAll  = VideoNewsAdapterV(this, videoList as ArrayList<VideoList>,
            object : NewsVideoRecyclerViewOnclick {
                override fun onClick(obj: VideoList) {
                    //moveToVideoDetailsScreen(obj)
                    playThisVideo(obj.path,obj.title,obj.createTime)
                    //player!!.playWhenReady=true
                }
            })

        recycler_view.adapter = adapterAll

        recycler_view.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            exo?.release()
            finish()
        }
    }

    private fun casting() {
        videoPlayer = findViewById<StyledPlayerView>(R.id.video_player)
        back_image = findViewById<ImageView>(R.id.back_image)
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        text_view = findViewById<TextView>(R.id.text_view)
        text_view_time = findViewById<TextView>(R.id.text_view_time)
        pro_bar_load_more = findViewById<ProgressBar>(R.id.pro_bar)
    }
    private fun fillABasicInfo() {
//        text_view.text = MySharableObject.videoNewsObject!!.title
//        text_view_time.text = MySharableObject.videoNewsObject!!.createTime?.substringBefore("T")
        var xx = MySharableObject.videoNewsObject!!.createTime?.substringBefore("T")
        handlePlayer(MySharableObject.videoNewsObject?.path!!,MySharableObject.videoNewsObject!!.title, xx!!)
        playerListener()
    }

    private fun handlePlayer(path: String,title:String,time:String) {
        player = ExoPlayer.Builder(this).build()
        exo=player
        player!!.setMediaItem(MediaItem.fromUri(path))
        player!!.prepare()

        videoPlayer.player = player
        playThisVideo(MySharableObject.videoNewsObject?.path!!,title,time)
        playerListener()
    }

    fun playThisVideo(videoLink:String,title:String,date:String){
        exo?.setMediaItem(MediaItem.fromUri(videoLink))
        exo?.seekToNextMediaItem()
        text_view.text = title
        text_view_time.text = date

    }

    private fun playerListener() {
        player?.addListener(object: Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState== Player.STATE_READY){
                    videoPlayer.hideController()
                }
                if (playbackState== Player.STATE_ENDED){
//                    if(view.findViewById<SwitchMaterial>(R.id.autoplay_switch).isChecked){
//                        val link=getNextLink(data)
//                        playThisVideo(link,geNextTitle(data),getNextTime(data))
//                        previousPosition++
//                    }
                }
            }
        })
        player!!.playWhenReady=true
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onDestroy() {
        super.onDestroy()
        exo?.release()
    }

}
