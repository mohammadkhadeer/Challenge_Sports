package com.example.view.videoDetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.model.newsVideo.VideoList
import com.example.presnter.NewsVideoRecyclerViewOnclick
import com.example.utils.MySharableObject
import com.example.view.mainActivity.homeAdapter.VideoAdapter.VideoNewsAdapterHorizontal
import com.example.view.videoDetails.suggestionVideos.VideoNewsAdapterV
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout

class VideoDetailsActivity : AppCompatActivity() {
    //ui
    private lateinit var videoPlayer: StyledPlayerView
    private lateinit var back_image: ImageView
    private lateinit var recycler_view: RecyclerView
    private lateinit var text_view: TextView
    private lateinit var text_view_time: TextView

    //value
    private var exo:ExoPlayer?=null
    private var player:ExoPlayer?=null
    private var videoList:  List<VideoList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)

        statusBarColor()
        casting()
        if (MySharableObject.videoNewsObject != null)
        {
            fillABasicInfo()
            if (MySharableObject.videoListObject != null){
                createASuggestionList()
            }
        }
        actionListenerToBack()

    }

    private fun createASuggestionList() {
        videoList = MySharableObject.videoListObject

        recycler_view.isNestedScrollingEnabled = false;

        recycler_view.adapter= VideoNewsAdapterV(this, videoList as ArrayList<VideoList>,
            object : NewsVideoRecyclerViewOnclick {
                override fun onClick(obj: VideoList) {
                    moveToVideoDetailsScreen(obj)
                }
            })

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

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            finish()
        }
    }

    private fun casting() {
        videoPlayer = findViewById<StyledPlayerView>(R.id.video_player)
        back_image = findViewById<ImageView>(R.id.back_image)
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        text_view = findViewById<TextView>(R.id.text_view)
        text_view_time = findViewById<TextView>(R.id.text_view_time)
    }
    private fun fillABasicInfo() {
        text_view.text = MySharableObject.videoNewsObject!!.title
        text_view_time.text = MySharableObject.videoNewsObject!!.createTime?.substringBefore("T")


        player = ExoPlayer.Builder(this).build()
        exo=player

        println(MySharableObject.videoNewsObject?.path!!)
        player!!.setMediaItem(MediaItem.fromUri(MySharableObject.videoNewsObject?.path!!))
        player!!.prepare()

        videoPlayer.player = player

        playerListener()
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


}