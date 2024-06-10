package com.example.view.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.view.mainActivity.homeFragments.MatchesFragment
import com.example.view.mainActivity.homeFragments.ProfileFragment
import com.example.apisetup.R
import com.example.presnter.OnSeeAllMatchesListener
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.view.allNewsVideo.AllNewsVideoActivity
import com.example.view.login.Login
import com.example.view.mainActivity.Discover.Frags.DiscoverFragment
import com.example.view.mainActivity.homeFragments.HomeFragment
import com.example.view.mainActivity.homeFragments.profileFragments.VideosFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() , OnSeeAllMatchesListener  {
    //ui
    private lateinit var bottomNavigationView: BottomNavigationView

    //fragments
    val home_fragment = HomeFragment()
    val profile_fragment  = ProfileFragment()
    val matchFrag     = MatchesFragment.newInstance()
    val profileFrag   = DiscoverFragment.newInstance("","")

    //fragment insaid inner fragment
    private var videosFragment = VideosFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity_drawer_container)

        statusBarColor()
        tabLayout()
    }

    private fun tabLayout() {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_tab -> {
                    replaceFragment(home_fragment)
                }
                R.id.matches_tab -> {
                    replaceFragment(matchFrag)
                }
                R.id.discover_tab -> {
                    replaceFragment(profileFrag)
                }
                R.id.profile_tab -> {
                    val userData = SharedPreferencesHelper.getUser(this)
                    if (userData?.token != null)
                    {
                        replaceFragment(profile_fragment)
                    }else{
                        moveToLoginScreen()
                    }

                }
            }
            true
        }

        replaceFragment(home_fragment)
        bottomNavigationView.selectedItemId = R.id.home_tab

        val addFab = findViewById<FloatingActionButton>(R.id.addFabBtn)
        addFab.setOnClickListener {
            val userData = SharedPreferencesHelper.getUser(this)
            moveToLoginScreen()
//            if (userData?.token != null)
//            {
//                Log.i("TAG","TAG userData?.token: "+userData?.token)
//                Log.i("TAG","TAG userData?.name: "+userData?.name)
//                Log.i("TAG","TAG userData?.email: "+userData?.email)
//            }else{
//                moveToLoginScreen()
//            }
        }
    }

    private fun moveToLoginScreen() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.bottom_up_enter, R.anim.bottom_up_exit)
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottomFragment,fragment)
            .commit()
    }

    override fun onPress(from_matches_or_video: String) {
        if (from_matches_or_video == "matches"){
            replaceFragment(matchFrag)
            bottomNavigationView.selectedItemId = R.id.matches_tab
        }else{
            val intent = Intent(this, AllNewsVideoActivity::class.java)
            startActivity(intent)
        }
    }

}