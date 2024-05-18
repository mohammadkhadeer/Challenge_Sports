package com.challenge.sports.view.HomeActivity.homeFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.hotMatches.HotMatche
import com.example.model.hotMatches.MatchStatusJ
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsRoot
import com.example.presnter.RecyclerViewOnclickMatch
import com.example.utils.GeneralTools
import com.example.utils.MySharableObject
import com.example.view.mainActivity.homeAdapter.matches.MatchesAdapter
import com.example.view.matchDetails.Adapters.AdapterOdds
import com.example.view.matchDetails.Adapters.AdapterOddsHandicap
import com.example.view.matchDetails.MatchDetails
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class OddsFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var vm: MyViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var recycler_view_handicap: RecyclerView

    private lateinit var teams_info_ll: LinearLayout
    private lateinit var teams_info_ll_handicap: LinearLayout

    private var oddsAdapter: AdapterOdds? = null
    private var oddsAdapterHandicap: AdapterOddsHandicap? = null

    private lateinit var header_text_1: TextView
    private lateinit var header_text_2: TextView
    private lateinit var header_text_3: TextView
    private var oddsList: List<List<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matche_odds_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)
        addATabsToTabLayout()
        tabLayoutController()

        vm = SpewViewModel.giveMeViewModel(requireActivity())
        if (MySharableObject.matchObject != null)
        {
            Log.i("TAG","TAG MySharableObject.matchObject?.id!! "+MySharableObject.matchObject?.id!!)
            vm.getMatchOdds(MySharableObject.matchObject?.id!!)
        }

        //odds type To win in response is asia
        createOddList("asia")
    }

    private fun addATabsToTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.to_win)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.handicap)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.goals)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.corners)))
    }

    private fun tabLayoutController() {

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0->{
                        Log.i("TAG" ,"TAG tabLayoutController")
                        Toast.makeText(context, "Tab to_win " + tab.position, Toast.LENGTH_LONG).show()
                        handleToWinHeader()

                        //odds type To win in response is asia
                        createOddList("asia")
                    }
                    1->{
                        Toast.makeText(context, "Tab handicap " + tab.position, Toast.LENGTH_LONG).show()
                        handleHandicapHeader()

                        //odds type handicap in response is asia
                        //createOddList("eu")
                        passADataToOddsAdapterHandicap()
                    }
                    2->{
                        Toast.makeText(context, "Tab goals " + tab.position, Toast.LENGTH_LONG).show()
                        handleGoalsHeader()

                        //odds type goals in response is asia
                        createOddList("bs")
                    }
                    3->{
                        Toast.makeText(context, "Tab corners " + tab.position, Toast.LENGTH_LONG).show()
                        handleCornersHeader()

                        //odds type corners in response is asia
                        createOddList("cr")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.getTabAt(0)?.select();
    }

    private fun handleCornersHeader() {
        header_text_1.text = context!!.getString(R.string.corners)
        header_text_2.text = context!!.getString(R.string.over)
        header_text_3.text = context!!.getString(R.string.under)

        recycler_view.isVisible = true
        recycler_view_handicap.isVisible = false

        teams_info_ll_handicap.isVisible = false
        teams_info_ll.isVisible = true
    }

    private fun handleGoalsHeader() {
        header_text_1.text = context!!.getString(R.string.goal)
        header_text_2.text = context!!.getString(R.string.over)
        header_text_3.text = context!!.getString(R.string.under)


        recycler_view.isVisible = true
        recycler_view_handicap.isVisible = false

        teams_info_ll_handicap.isVisible = false
        teams_info_ll.isVisible = true
    }

    private fun handleHandicapHeader() {
        recycler_view.isVisible = false
        recycler_view_handicap.isVisible = true

        teams_info_ll_handicap.isVisible = true
        teams_info_ll.isVisible = false
    }

    private fun handleToWinHeader() {
        header_text_1.text = context!!.getString(R.string.home_team)
        header_text_2.text = context!!.getString(R.string.x)
        header_text_3.text = context!!.getString(R.string.away)

        recycler_view.isVisible = true
        recycler_view_handicap.isVisible = false

        teams_info_ll_handicap.isVisible = false
        teams_info_ll.isVisible = true
    }

    private fun casting(view: View) {
        //odds_recycler_view_handicap
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutOddsFragments)
        recycler_view = view.findViewById<RecyclerView>(R.id.odds_recycler_view)
        recycler_view_handicap = view.findViewById<RecyclerView>(R.id.odds_recycler_view_handicap)

        teams_info_ll = view.findViewById<LinearLayout>(R.id.teams_info_ll)
        teams_info_ll_handicap = view.findViewById<LinearLayout>(R.id.teams_info_ll_handicap)

        header_text_1 = view.findViewById<TextView>(R.id.header_text_1)
        header_text_2 = view.findViewById<TextView>(R.id.header_text_2)
        header_text_3 = view.findViewById<TextView>(R.id.header_text_3)
    }


    private fun createOddList(odds_type:String) {
        vm.matches_odds.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                var odd_list_data = GeneralTools.filterOddRoot(2,it.data!!,odds_type)
                oddsAdapter = AdapterOdds(requireContext(),odd_list_data)
                recycler_view.adapter = oddsAdapter
                recycler_view.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }else{
                Log.i("TAG" ,"data.status "+it.status)
            }
        }
    }



    private fun passADataToOddsAdapterHandicap() {
        vm.matches_odds.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                oddsAdapterHandicap = AdapterOddsHandicap(requireContext(), it.data?.get(0)!!.oddlist!!)

                recycler_view_handicap.adapter = oddsAdapterHandicap
                recycler_view_handicap.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }else{
                Log.i("TAG" ,"data.status "+it.status)
            }
        }

    }


}