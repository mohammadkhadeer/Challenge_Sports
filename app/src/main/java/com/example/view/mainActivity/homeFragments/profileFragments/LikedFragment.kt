package com.example.view.mainActivity.homeFragments.profileFragments

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.news.NewsBase
import com.example.model.userVideos.Response
import com.example.presnter.RecyclerViewOnclick
import com.example.utils.MySharableObjectViewModel
import com.example.view.allNews.AllNewsActivity
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.mainActivity.homeAdapter.profileVideos.ProfileVideoAdapter
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class LikedFragment : Fragment() {

    //ui
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: RelativeLayout
    private lateinit var progressBar: ProgressBar

    //value
    private var fragmentContext: Context? = null
    private lateinit var adapterVideos: ProfileVideoAdapter

    //server
    private lateinit var view_model: MyViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.liked_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        //server
        if(MySharableObjectViewModel.viewModel != null)
        {
            view_model = MySharableObjectViewModel.viewModel!!
            view_model.getALikedVideos()
            observeAResponse()
        }

    }

    private fun fillABasicInfoInTheFragment(response: Response) {
        recyclerView.isNestedScrollingEnabled = false

        if (fragmentContext != null)
        {
            hideAProgressAndShowNoDataOnceADAtaSizeIsZero(response.data.isEmpty())

            adapterVideos = ProfileVideoAdapter(fragmentContext!!,response.data)

            recyclerView.adapter = adapterVideos

            recyclerView.layoutManager =
                GridLayoutManager(fragmentContext!!, 3)
        }
    }

    private fun hideAProgressAndShowNoDataOnceADAtaSizeIsZero(empty: Boolean) {
        progressBar.isVisible = false
        if (empty)
            emptyView.isVisible = true
    }

    private fun observeAResponse() {
        view_model.userVideo3.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                fillABasicInfoInTheFragment(it.data!!.response)
            }else{
                if (it.status == Status.ERROR){

                }
            }
        }
    }

    private fun casting(view: View) {
        recyclerView = view.findViewById<RecyclerView> (R.id.recycler_view)
        emptyView    = view.findViewById<RelativeLayout> (R.id.empty_view)
        progressBar  = view.findViewById<ProgressBar> (R.id.pro_bar)
    }

}