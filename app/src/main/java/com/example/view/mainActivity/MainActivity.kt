package com.example.view.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.challenge.sports.view.HomeActivity.homeFragments.MatchesFragment
import com.challenge.sports.view.HomeActivity.homeFragments.ProfileFragment
import com.example.apisetup.R
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.MySharableObject
import com.example.view.login.Login
import com.example.view.mainActivity.Discover.Frags.DiscoverFragment
import com.example.view.mainActivity.homeFragments.NewsFragment
import com.example.view.matchDetails.MatchDetails
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    val newsFrag= NewsFragment.newInstance(false,":")
    val homeFragment= ProfileFragment.newInstance()
    val matchFrag= MatchesFragment.newInstance()
    val profileFrag= DiscoverFragment.newInstance("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity_drawer_container)

        statusBarColor()
        tabLayout()
    }

    private fun tabLayout() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_tab -> {
                    replaceFragment(newsFrag)
                }
                R.id.matches_tab -> {
                    replaceFragment(matchFrag)
                }
                R.id.discover_tab -> {
                    replaceFragment(profileFrag)
                }
                R.id.profile_tab -> {
                    replaceFragment(homeFragment)
                }
            }
            true
        }

        replaceFragment(newsFrag)
        bottomNavigationView.selectedItemId = R.id.matches_tab

        val addFab = findViewById<FloatingActionButton>(R.id.addFabBtn)
        val userData = SharedPreferencesHelper.getUser(this)
        addFab.setOnClickListener {
            moveToLoginScreen()
//            if (userData?.token != null)
//            {
//                Log.i("TAG","TAG userData not null")
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

}