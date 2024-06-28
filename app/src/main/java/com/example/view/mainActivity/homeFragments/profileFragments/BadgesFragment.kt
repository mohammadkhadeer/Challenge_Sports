package com.example.view.mainActivity.homeFragments.profileFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.utils.MySharableObjectViewModel
import com.example.view.mainActivity.homeAdapter.profileVideos.ProfileBadgesVideoAdapter
import com.example.viewmodel.MyViewModel

class BadgesFragment : Fragment() {

    //ui
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: RelativeLayout
    private lateinit var progressBar: ProgressBar

    //value
    private var fragmentContext: Context? = null
    private lateinit var adapterVideos: ProfileBadgesVideoAdapter

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
        return inflater.inflate(R.layout.badges_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        //server
        if(MySharableObjectViewModel.viewModel != null)
        {
            view_model = MySharableObjectViewModel.viewModel!!
            view_model.getABadgesVideos()
            observeAResponse()
        }
    }

    private fun fillABasicInfoInTheFragment(response: com.example.model.badgesVideo.Response) {
        recyclerView.isNestedScrollingEnabled = false

        if (fragmentContext != null)
        {
            hideAProgressAndShowNoDataOnceADAtaSizeIsZero(response.data.isEmpty())

            adapterVideos = ProfileBadgesVideoAdapter(fragmentContext!!,response.data)

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
        view_model.userVideo4.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                fillABasicInfoInTheFragment(it.data!!.response)
            }else{
                if (it.status == Status.ERROR){
                    hideAProgressAndShowNoDataOnceADAtaSizeIsZero(true)
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