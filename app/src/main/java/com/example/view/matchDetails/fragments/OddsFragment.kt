package com.challenge.sports.view.HomeActivity.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.apisetup.R
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class OddsFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var vm: MyViewModel

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
        vm.getMatchOdds("pxwrxlh52494ryk")
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
                        Toast.makeText(context, "Tab to_win " + tab.position, Toast.LENGTH_LONG).show()
                    }
                    1->{
                        Toast.makeText(context, "Tab handicap " + tab.position, Toast.LENGTH_LONG).show()
                    }
                    2->{
                        Toast.makeText(context, "Tab goals " + tab.position, Toast.LENGTH_LONG).show()
                    }
                    2->{
                        Toast.makeText(context, "Tab corners " + tab.position, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.getTabAt(0)?.select();
    }

    private fun casting(view: View) {
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutOddsFragments)
    }

}