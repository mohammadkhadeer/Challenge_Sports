package com.example.view.mainActivity.homeFragments.homeFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.hotMatches.HotMatche
import com.example.model.news.NewsBase
import com.example.model.newsVideo.VideoList
import com.example.model.newsVideo.VideoRoot
import com.example.presnter.NewsVideoRecyclerViewOnclick
import com.example.presnter.OnSeeAllMatchesListener
import com.example.presnter.RecyclerViewOnclick
import com.example.presnter.RecyclerViewOnclickMatch
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.MySharableObject
import com.example.view.allNews.AllNewsActivity
import com.example.view.mainActivity.homeAdapter.VideoAdapter.VideoNewsAdapterHorizontal
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeAdapter.matches.MatchesAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.matchDetails.MatchDetails
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.view.videoDetails.VideoDetailsActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class VideoFragment : Fragment() {

    //ui
    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var pro_bar: ProgressBar
    private lateinit var see_all_img: ImageView

    //value
    private var fragmentContext: Context? = null
    private var mainAdapter: MatchesAdapter? = null
    private var listener: OnSeeAllMatchesListener? = null
    private var videoList:  List<VideoList>? = null

    //server
    private lateinit var view_model: MyViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context

        if (context is OnSeeAllMatchesListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(requireActivity())

        var lang = SharedPreferencesHelper.getALanguage(requireActivity())!!
        if (lang == "zh")
            lang = "cn"
        view_model.getANewVideo(lang,"1")

        actionListenerToSeeAll()
        //observe banner response
        observeResponse()
    }

    private fun observeResponse() {
        view_model.newsVideoData.observe(this){
            if (it.status== Status.SUCCESS){
                if (fragmentContext != null){
                    pro_bar.isVisible = false

                    populateRecyclerViews(it.data!!)
                }

            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun populateRecyclerViews(data: VideoRoot) {
        videoList = data?.list

        recyclerViewMain.isNestedScrollingEnabled = false;

        recyclerViewMain.adapter= VideoNewsAdapterHorizontal(requireContext(), videoList!! as ArrayList<VideoList>,
            object : NewsVideoRecyclerViewOnclick {
                override fun onClick(obj: VideoList) {
                    moveToVideoDetailsScreen(obj)
                }
            })

        recyclerViewMain.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
    }

    private fun moveToVideoDetailsScreen(obj: VideoList) {
        videoList!!.filter {  it.id != obj.id }

        MySharableObject.videoListObject = videoList
        MySharableObject.videoNewsObject = obj
        val intent = Intent(requireActivity(), VideoDetailsActivity::class.java)
        startActivity(intent)
    }

    private fun actionListenerToSeeAll() {
        see_all_img.setOnClickListener {
            MySharableObject.videoListObject = videoList
            listener?.onPress("videos")
        }
    }

    private fun casting(view: View) {
        //view_pager_ads = view.findViewById<ViewPager2>(R.id.viewpager)
        recyclerViewMain= view.findViewById<RecyclerView>(R.id.horizontal_rv)
        pro_bar= view.findViewById<ProgressBar>(R.id.pro_bar)
        see_all_img = view.findViewById<ImageView>(R.id.see_all_img)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}