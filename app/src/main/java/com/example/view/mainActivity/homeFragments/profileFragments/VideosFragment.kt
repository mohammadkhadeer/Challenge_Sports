package com.example.view.mainActivity.homeFragments.profileFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.view.mainActivity.homeAdapter.profileVideos.ProfileVideoAdapter
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class VideosFragment : Fragment() {

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
        return inflater.inflate(R.layout.profile_videos_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        view_model = SpewViewModel.giveMeViewModel(requireActivity())
        //sent a requisite to get a banner ads
//        view_model.getNews("1","en")

        //observe banner response
        observeBannerResponse()
        createAList()
    }

    private fun createAList() {

        recyclerView.isNestedScrollingEnabled = false

        if (fragmentContext != null)
        {
            adapterVideos = ProfileVideoAdapter(fragmentContext!!)

            recyclerView.adapter = adapterVideos

            recyclerView.layoutManager =
                GridLayoutManager(fragmentContext!!, 3)
        }

    }

    private fun observeBannerResponse() {

    }

    private fun casting(view: View) {
        recyclerView = view.findViewById<RecyclerView> (R.id.recycler_view)
        emptyView    = view.findViewById<RelativeLayout> (R.id.empty_view)
        progressBar  = view.findViewById<ProgressBar> (R.id.pro_bar)
    }

    fun updateData() {
        // Process the data here
        Log.d("ChildFragment", "VideosFragment Data received:")
    }
}