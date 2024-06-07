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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.news.NewsBase
import com.example.presnter.RecyclerViewOnclick
import com.example.view.allNews.AllNewsActivity
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class SavedFragment : Fragment() {

    //ui
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: RelativeLayout
    private lateinit var progressBar: ProgressBar

    //value
    private var fragmentContext: Context? = null

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
        return inflater.inflate(R.layout.saved_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        view_model = SpewViewModel.giveMeViewModel(requireActivity())
        //sent a requisite to get a banner ads
//        view_model.getNews("1","en")

        //observe banner response
        observeBannerResponse()

    }

    private fun observeBannerResponse() {

    }


    private fun casting(view: View) {
        recyclerView = view.findViewById<RecyclerView> (R.id.recycler_view)
        emptyView    = view.findViewById<RelativeLayout> (R.id.empty_view)
        progressBar  = view.findViewById<ProgressBar> (R.id.pro_bar)
    }

}