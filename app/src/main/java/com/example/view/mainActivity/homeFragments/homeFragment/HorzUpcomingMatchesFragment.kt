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
import com.example.presnter.OnSeeAllMatchesListener
import com.example.presnter.RecyclerViewOnclick
import com.example.presnter.RecyclerViewOnclickMatch
import com.example.utils.MySharableObject
import com.example.view.allNews.AllNewsActivity
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeAdapter.matches.MatchesAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.matchDetails.MatchDetails
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class HorzUpcomingMatchesFragment : Fragment() {

    //ui
    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var pro_bar: ProgressBar
    private lateinit var see_all_img: ImageView
    private lateinit var empty_view: RelativeLayout

    //value
    private var fragmentContext: Context? = null
    private var mainAdapter: MatchesAdapter? = null
    private var listener: OnSeeAllMatchesListener? = null

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
        return inflater.inflate(R.layout.upcoming_matches_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(requireActivity())
        //sent a requisite to get a banner ads
        view_model.getUpcomingMatches()

        actionListenerToSeeAll()
        //observe banner response
        observeBannerResponse()
    }

    private fun actionListenerToSeeAll() {
        see_all_img.setOnClickListener {
            listener?.onPress("matches")
        }
    }


    private fun observeBannerResponse() {
        view_model.matches_upcoming.observe(this){
            if (it.status== Status.SUCCESS){
                if (fragmentContext != null){
                    pro_bar.isVisible = false

                    passADataToMainAdapter(it.data!!.matchList)
                }

            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun passADataToMainAdapter(matches: List<HotMatche?>?) {

        if (matches?.size == 0)
        {
            empty_view.isVisible =true
        }else{

            if (fragmentContext != null){

                mainAdapter = MatchesAdapter(
                    fragmentContext!!,
                    matches!!
                    , object : RecyclerViewOnclickMatch {
                        override fun onClick(position: Int, match_obj: HotMatche) {
                            val intent = Intent(requireContext(), MatchDetails::class.java)
                            MySharableObject.matchObject=match_obj
                            startActivity(intent)
                        }
                    })

                recyclerViewMain.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                // Calling the override functions from
                // the Linear Layout Manager inner class
                //freeze scroll
                val myLinearLayoutManager = object : LinearLayoutManager(requireContext()) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }

                recyclerViewMain.layoutManager = myLinearLayoutManager

                recyclerViewMain.adapter = mainAdapter

                //handel what should be visible what should be gone
                recyclerViewMain.isVisible = true
                empty_view.isVisible = false
                pro_bar.isVisible = false
            }

        }

    }

    private fun casting(view: View) {
        //view_pager_ads = view.findViewById<ViewPager2>(R.id.viewpager)
        recyclerViewMain= view.findViewById<RecyclerView>(R.id.horizontal_rv)
        pro_bar= view.findViewById<ProgressBar>(R.id.pro_bar)
        see_all_img = view.findViewById<ImageView>(R.id.see_all_img)
        empty_view = view.findViewById<RelativeLayout>(R.id.empty_view)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}