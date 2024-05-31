package com.example.view.mainActivity.homeFragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.banner.Top
import com.example.presnter.RecyclerViewOnclickAds
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeFragments.homeFragment.HorzNewsFragment
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class HomeFragment : Fragment() , ImageSliderAdapter.OnItemClickListener{
    //ui
    private lateinit var view_pager_ads: ViewPager2
    private lateinit var pro_bar: ProgressBar

    //value
    private var fragmentContext: Context? = null
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private var addsListSize = 0

    //server
    private lateinit var view_model: MyViewModel

    private val updatePage = object : Runnable {
        override fun run() {
            if (currentPage == addsListSize) {
                currentPage = 0
            }
            view_pager_ads.setCurrentItem(currentPage++, true)
            handler.postDelayed(this, 3000) // Change image every 3 seconds
        }
    }

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
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        // Add fragments to the container
        addFragmentToContainer(HorzNewsFragment())

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(requireActivity())
        //sent a requisite to get a banner ads
        view_model.getABannerAds()

        //observe banner response
        observeBannerResponse()
    }

    private fun observeBannerResponse() {
        view_model.bannerRoot.observe(this){
            if (it.status== Status.SUCCESS){
                if (fragmentContext != null){
                    pro_bar.isVisible = false

                    view_pager_ads.adapter = ImageSliderAdapter(fragmentContext!!,it.data!!.data.top,this)

                    addsListSize = it.data!!.data.top.size
                    handler.post(updatePage)
                }

            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun casting(view: View) {
        view_pager_ads = view.findViewById<ViewPager2>(R.id.viewpager)
        pro_bar = view.findViewById<ProgressBar>(R.id.pro_bar)
        //to freeze view pager slid option
        view_pager_ads.isUserInputEnabled = false
    }

    private fun addFragmentToContainer(fragment: Fragment) {
        // Get the FragmentManager and start a transaction
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Add the fragment to the container
        fragmentTransaction.add(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    override fun onItemClick(adObj: Top) {
        Log.i("TAG" ,"adObj "+ adObj.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updatePage) // Stop the handler when the activity is destroyed
    }
}
