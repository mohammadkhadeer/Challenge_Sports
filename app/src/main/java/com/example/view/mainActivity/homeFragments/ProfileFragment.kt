package com.example.view.mainActivity.homeFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.presnter.ViewPagerAdapter
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.view.mainActivity.homeFragments.profileFragments.BadgesFragment
import com.example.view.mainActivity.homeFragments.profileFragments.LikedFragment
import com.example.view.mainActivity.homeFragments.profileFragments.SavedFragment
import com.example.view.mainActivity.homeFragments.profileFragments.UserHeaderFragment
import com.example.view.mainActivity.homeFragments.profileFragments.VideosFragment
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList

class ProfileFragment : Fragment() {

    //ui
    private lateinit var tabLayout: TabLayout
    private lateinit var nestedScrollView: NestedScrollView

    //values
    private lateinit var baseViewPager: ViewPager2
    private lateinit var fragsList: ArrayList<Fragment>
    private var fragmentContext: Context? = null

    //fragments
    private var videosFragment = VideosFragment()
    private var badgesFragment = BadgesFragment()
    private var savedFragment  = SavedFragment()
    private var likedFragment  = LikedFragment()

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
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addFragmentToContainer(UserHeaderFragment())
        casting(view)

        fillAFragmentInArrayList()
        handleAViewPagerAdapter()
        tabLayoutController()

        checkIfNestedScrollViewTouchBottom()

        //server
//        view_model = SpewViewModel.giveMeViewModelWithHeaderFromFragment(requireActivity())
//
//        view_model.getBasicInfoRequest()
//        observeAResponse()
    }

    private fun observeAResponse() {
        view_model.basicProfile.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                SharedPreferencesHelper.saveProfileInfo(requireActivity(), it.data!!.response.data)

            }else{
                if (it.status == Status.ERROR){

                }

            }
        }
    }

    private fun checkIfNestedScrollViewTouchBottom() {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                Log.i("TAG", "BOTTOM SCROLL")
                videosFragment.updateData()
            }
        })
    }

    private fun fillAFragmentInArrayList() {
        fragsList= ArrayList<Fragment>()
        fragsList.add(videosFragment)
        fragsList.add(badgesFragment)
        fragsList.add(savedFragment)
        fragsList.add(likedFragment)
    }
    private fun handleAViewPagerAdapter() {
        //getChildFragmentManager()
        baseViewPager.adapter= ViewPagerAdapter(childFragmentManager,lifecycle,fragsList)
        //should to call this method if we want to have a adapter tablayout in fragment
        baseViewPager.isSaveEnabled = false
        baseViewPager.isUserInputEnabled=false
        baseViewPager.offscreenPageLimit=3

    }

    private fun tabLayoutController() {
        TabLayoutMediator(tabLayout,baseViewPager){ tab , position ->
            when(position){
                0->{
                    tab.icon= ContextCompat.getDrawable(fragmentContext!!, R.drawable.videos_icon)
                }
                1->{
                    tab.icon= ContextCompat.getDrawable(fragmentContext!!, R.drawable.badges_ic)
                }
                2->{
                    tab.icon= ContextCompat.getDrawable(fragmentContext!!, R.drawable.bookmark_ic)
                }
                3->{
                    tab.icon= ContextCompat.getDrawable(fragmentContext!!, R.drawable.like_ic)
                }
            }
        }.attach()

        tabLayout.getTabAt(0)?.select()
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
        tabLayout        = view.findViewById<TabLayout>(R.id.tabLayoutUser)
        baseViewPager    = view.findViewById<ViewPager2>(R.id.baseViewPagerProfileScreen)
        nestedScrollView = view.findViewById<NestedScrollView>(R.id.nested_scroll_view)
    }

}
