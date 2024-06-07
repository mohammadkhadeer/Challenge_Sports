package com.example.view.mainActivity.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.presnter.ViewPagerAdapter
import com.example.view.mainActivity.Discover.Frags.StickyHeaderItemDecoration
import com.example.view.mainActivity.Discover.Frags.adapters.ProfileViewAdapter
import com.example.view.mainActivity.Discover.Frags.adapters.containerModel.ProfileFragRvDataClass
import com.example.view.mainActivity.homeFragments.homeFragment.HorzNewsFragment
import com.example.view.mainActivity.homeFragments.profileFragments.UserHeaderFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    //ui
    private lateinit var tabLayout: TabLayout

    //values
    private lateinit var baseViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addFragmentToContainer(UserHeaderFragment())
        casting(view)
        handleAViewPagerAdapter()
        //tabLayoutController()
    }

    private fun handleAViewPagerAdapter() {
//        baseViewPager.adapter= ViewPagerAdapter(requireContext().supportFragmentManager,lifecycle,fragsList)
//        baseViewPager.isUserInputEnabled=false
//        baseViewPager.offscreenPageLimit=3
    }

    private fun tabLayoutController() {
        TabLayoutMediator(tabLayout,baseViewPager){tab,position->
            when(position){
                0->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
                1->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
                2->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
                3->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.ic_home)
                }
            }
        }.attach()

        tabLayout.getTabAt(1)?.select();
    }

    private fun addFragmentToContainer(fragment: Fragment) {
        // Get the FragmentManager and start a transaction
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Add the fragment to the container
        fragmentTransaction.add(R.id.fragmentProfileHeaderContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun casting(view: View) {
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutUser)
    }
}