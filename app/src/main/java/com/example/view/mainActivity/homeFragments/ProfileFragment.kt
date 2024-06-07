package com.example.view.mainActivity.homeFragments

import android.content.Context
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
import com.challenge.sports.view.HomeActivity.homeFragments.MatchInfoFragment
import com.example.apisetup.R
import com.example.presnter.ViewPagerAdapter
import com.example.view.mainActivity.Discover.Frags.StickyHeaderItemDecoration
import com.example.view.mainActivity.Discover.Frags.adapters.ProfileViewAdapter
import com.example.view.mainActivity.Discover.Frags.adapters.containerModel.ProfileFragRvDataClass
import com.example.view.mainActivity.homeFragments.homeFragment.HorzNewsFragment
import com.example.view.mainActivity.homeFragments.profileFragments.BadgesFragment
import com.example.view.mainActivity.homeFragments.profileFragments.LikedFragment
import com.example.view.mainActivity.homeFragments.profileFragments.SavedFragment
import com.example.view.mainActivity.homeFragments.profileFragments.UserHeaderFragment
import com.example.view.mainActivity.homeFragments.profileFragments.VideosFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class ProfileFragment : Fragment() {

    //ui
    private lateinit var tabLayout: TabLayout

    //values
    private lateinit var baseViewPager: ViewPager2
    private lateinit var fragsList: ArrayList<Fragment>
    private var fragmentContext: Context? = null

    //fragments
    private var videosFragment = VideosFragment()
    private var badgesFragment = BadgesFragment()
    private var savedFragment  = SavedFragment()
    private var likedFragment  = LikedFragment()

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
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addFragmentToContainer(UserHeaderFragment())
        casting(view)

        fillAFragmentInArrayList()
        handleAViewPagerAdapter()
        tabLayoutController()
    }

    private fun fillAFragmentInArrayList() {
        fragsList= ArrayList<Fragment>()
        fragsList.add(videosFragment)
        fragsList.add(badgesFragment)
        fragsList.add(savedFragment)
        fragsList.add(likedFragment)
    }
    private fun handleAViewPagerAdapter() {
        baseViewPager.adapter= ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle,fragsList)
        baseViewPager.isUserInputEnabled=false
        baseViewPager.offscreenPageLimit=3
    }

    private fun tabLayoutController() {
        TabLayoutMediator(tabLayout,baseViewPager){tab,position->
            when(position){
                0->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.videos_icon)
                }
                1->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.badges_ic)
                }
                2->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.bookmark_ic)
                }
                3->{
                    tab.icon= ContextCompat.getDrawable(requireContext(), R.drawable.like_ic)
                }
            }
        }.attach()

        tabLayout.getTabAt(0)?.select();
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
        tabLayout     = view.findViewById<TabLayout>(R.id.tabLayoutUser)
        baseViewPager = view.findViewById<ViewPager2>(R.id.baseViewPagerProfileScreen)
    }
}