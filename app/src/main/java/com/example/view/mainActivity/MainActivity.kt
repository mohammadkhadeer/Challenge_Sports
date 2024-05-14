package com.example.view.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.challenge.sports.view.HomeActivity.homeFragments.MatchesFragment
import com.challenge.sports.view.HomeActivity.homeFragments.ProfileFragment
import com.example.apisetup.R
import com.example.presnter.ViewPagerAdapter
import com.example.view.mainActivity.homeFragments.NewsFragment
import com.example.view.mainActivity.homeFragments.StandingBaseFragment
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class MainActivity : AppCompatActivity() {

    val newsFrag= NewsFragment.newInstance(false,":")
    val homeFragment= ProfileFragment.newInstance()
    val matchFrag= MatchesFragment.newInstance()
    val profileFrag= ProfileFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusBarColor()
        tabLayout()

    }

    private fun tabLayout() {
        val baseViewPager=findViewById<ViewPager2>(R.id.baseViewPager)

        val fragsList= ArrayList<Fragment>()
        fragsList.add(newsFrag)
        fragsList.add(matchFrag)
        fragsList.add(StandingBaseFragment.newInstance("",""))
        fragsList.add(homeFragment)
        fragsList.add(profileFrag)



        baseViewPager.adapter= ViewPagerAdapter(supportFragmentManager,lifecycle,fragsList)
        baseViewPager.isUserInputEnabled=false
        baseViewPager.offscreenPageLimit=4

        val tabLayout=findViewById<TabLayout>(R.id.tabLayoutMain)
        TabLayoutMediator(tabLayout,baseViewPager){tab,position->
            when(position){
                0->{
                    tab.text=getString(R.string.home)
                    tab.icon=getDrawable(R.drawable.ic_home)
                }
                1->{
                    tab.text=getString(R.string.matches)
                    tab.icon= ContextCompat.getDrawable(this,R.drawable.matches)
                }
                2->{
                    tab.text=" "
                    tab.icon= ContextCompat.getDrawable(this,R.drawable.upload)

                }
                3->{
                    tab.text=getString(R.string.discover)
                    tab.icon= ContextCompat.getDrawable(this,R.drawable.discoveries)
                }
                4->{
                    tab.text=getString(R.string.profile)
                    tab.icon= ContextCompat.getDrawable(this,R.drawable.profile_ic)
                }
            }
        }.attach()

        //to detect tab bar will start from where
        tabLayout.getTabAt(1)?.select();
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}