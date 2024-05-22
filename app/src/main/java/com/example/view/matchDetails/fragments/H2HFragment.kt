package com.challenge.sports.view.HomeActivity.homeFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.utils.GeneralTools
import com.example.utils.MySharableObject
import com.example.view.matchDetails.Adapters.AdapterOdds
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout

class H2HFragment : Fragment() {
    //ui
    private lateinit var tabLayout: TabLayout

    //values
    private lateinit var vm: MyViewModel
    private var selected_tab: String = "home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matche_h2h_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        addATabsToTabLayout()

        tabLayoutController()

        vm = SpewViewModel.giveMeViewModel(requireActivity())
        if (MySharableObject.matchObject != null)
        {
            Log.i("TAG","TAG H2HFragment MySharableObject.matchObject?.id!! "+ MySharableObject.matchObject?.id!!)
            vm.getH2HListMatches(MySharableObject.matchObject?.id!!)
        }

        handleARecyclerView(selected_tab)
    }

    private fun casting(view: View) {
        //odds_recycler_view_handicap
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutH2HFragments)
    }

    private fun addATabsToTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home_team)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.away)))
    }

    private fun tabLayoutController() {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0->{
                        selected_tab = "home"
                        handleARecyclerView(selected_tab)
                    }

                    1->{
                        selected_tab = "away"
                        handleARecyclerView(selected_tab)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.getTabAt(0)?.select();
    }

    private fun handleARecyclerView(selectedTab: String) {

        vm.h2hList.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                var h2hMatchList = GeneralTools.getH2HListMatches(selectedTab,it.data!!.history)

            }else{
                Log.i("TAG" ,"data.status "+it.status)
            }
        }

    }


}