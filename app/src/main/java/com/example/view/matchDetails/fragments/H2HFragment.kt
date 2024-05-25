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
import com.example.model.headToHeadMatches.History
import com.example.utils.GeneralTools
import com.example.utils.MySharableObject
import com.example.view.matchDetails.Adapters.AdapterH2HMatches
import com.example.view.matchDetails.Adapters.AdapterOdds
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout

class H2HFragment : Fragment() {
    //ui
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var horizontalProgressView: ProgressBar
    private lateinit var number_of_win_txt: TextView
    private lateinit var total_matches_txt: TextView
    private lateinit var number_of_lose_txt: TextView
    private lateinit var per_text: TextView

    //values
    private lateinit var vm: MyViewModel
    private var selected_tab: String = "home"
    private var h2hMatchesAdapter: AdapterH2HMatches? = null

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
            vm.getH2HListMatches(MySharableObject.matchObject?.id!!)
        }

        handleARecyclerView(selected_tab)
    }

    private fun casting(view: View) {
        //odds_recycler_view_handicap
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutH2HFragments)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        horizontalProgressView = view.findViewById<ProgressBar>(R.id.progressBar)
        number_of_win_txt = view.findViewById<TextView>(R.id.number_of_win_txt)
        total_matches_txt = view.findViewById<TextView>(R.id.total_matches_txt)
        number_of_lose_txt = view.findViewById<TextView>(R.id.number_of_lose_txt)
        per_text = view.findViewById<TextView>(R.id.per_text)
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

                fillProgressViewInfo(it.data!!.history,selectedTab)

                var h2hMatchList = GeneralTools.getH2HListMatches(selectedTab,it.data!!.history)

                //h2hMatchesAdapter
                h2hMatchesAdapter = AdapterH2HMatches(requireContext(),h2hMatchList)
                recyclerView.adapter = h2hMatchesAdapter
                recyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }else{
                Log.i("TAG" ,"data.status "+it.status)
            }
        }

    }

    private fun fillProgressViewInfo(history: History,home_or_away:String) {
       // Between 0 and 100 (%)
        var statisticsList = GeneralTools.getSomeOfStatisticsAboutMatch(history,
            MySharableObject.matchObject?.homeInfo?.enName!!,MySharableObject.matchObject?.awayInfo?.enName!!)

        if (home_or_away == "home")
        {
            var x = statisticsList[6].toDouble() * 100
            var y = statisticsList[8].toDouble() * 100
            var xy = x.toInt()
            var xyz = y.toInt()
            horizontalProgressView.progress = xy
            per_text.text = "(" +xy.toString() + "%" + " " + requireActivity().getString(R.string.won) + " / " + xyz.toString() + "%" + requireActivity().getString(R.string.lose) + ")"

            number_of_win_txt.text = "(${statisticsList[0]} / ${statisticsList[4]})"
            number_of_lose_txt.text = "(${statisticsList[1]} / ${statisticsList[4]})"

            total_matches_txt.text = statisticsList[4].toString() + " " + requireActivity().getString(R.string.matches)

        }else{
            var x = statisticsList[7].toDouble() * 100
            var y = statisticsList[9].toDouble() * 100
            var xy = x.toInt()
            var xyz = y.toInt()
            horizontalProgressView.progress = xy
            per_text.text = "(" + xy.toString() + "%" + " " + requireActivity().getString(R.string.won) + " / " + xyz.toString() + "%" + requireActivity().getString(R.string.lose) + ")"

            number_of_win_txt.text = "(${statisticsList[2]} / ${statisticsList[5]})"
            number_of_lose_txt.text = "(${statisticsList[3]} / ${statisticsList[5]})"

            total_matches_txt.text = statisticsList[5].toString() + " " + requireActivity().getString(R.string.matches)

        }

    }


}