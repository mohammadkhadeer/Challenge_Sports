package com.example.view.mainActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.apisetup.R
import com.example.presnter.OnSeeAllMatchesListener
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.utils.MySharableObjectViewModel
import com.example.view.allNewsVideo.AllNewsVideoActivity
import com.example.view.login.Login
import com.example.view.mainActivity.Discover.Frags.DiscoverFragment
import com.example.view.mainActivity.homeFragments.HomeFragment
import com.example.view.mainActivity.homeFragments.MatchesFragment
import com.example.view.mainActivity.homeFragments.ProfileFragment
import com.example.view.mainActivity.homeFragments.profileFragments.VideosFragment
import com.example.view.uploadVideo.UploadVideoActivity
import com.example.viewmodel.SpewViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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
        // create a view_model instance here where if we send a requisite from profile fragment we
        //will get a 401 error i don't know why
        MySharableObjectViewModel.viewModel = SpewViewModel.giveMeViewModelWithHeader(this@MainActivity)
//        println("TAG "+ SharedPreferencesHelper.getAToken(this))
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
//            moveToLoginScreen()
            if (userData?.token != null)
            {
                moveToUploadVideo()
            }else{
                moveToLoginScreen()
            }
        }
    }

    private fun moveToUploadVideo() {
        val intent = Intent(this, UploadVideoActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.bottom_up_enter, R.anim.bottom_up_exit)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // i call this override to can use it in child fragment
        //the fragment name is ProfileFragment
    }

}