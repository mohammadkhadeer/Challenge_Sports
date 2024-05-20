package com.example.view.matchDetails

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.challenge.sports.view.HomeActivity.homeFragments.H2HFragment
import com.challenge.sports.view.HomeActivity.homeFragments.MatchInfoFragment
import com.challenge.sports.view.HomeActivity.homeFragments.OddsFragment
import com.challenge.sports.view.HomeActivity.homeFragments.ProfileFragment
import com.example.apisetup.R
import com.example.model.odds.OddsCompanyComp
import com.example.presnter.FillCompanyInfo
import com.example.presnter.ViewPagerAdapter
import com.example.utils.GeneralTools
import com.example.utils.MySharableObject
import com.example.utils.SelectedCompanyObj
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.sql.DriverManager
import java.util.*

class MatchDetails : AppCompatActivity() , FillCompanyInfo {
    private lateinit var back_image: ImageView

    private lateinit var home_image: ImageView
    private lateinit var away_image: ImageView
    private lateinit var leagua_image: ImageView

    private lateinit var leagueNameShort : TextView
    private lateinit var home_team_name : TextView
    private lateinit var away_team_name : TextView
    private lateinit var match_score : TextView

    private lateinit var weather_status_txt : TextView
    private lateinit var weather_status_image: ImageView

    private lateinit var match_time : TextView
    private lateinit var match_date : TextView

    private lateinit var tabLayout: TabLayout
    private lateinit var baseViewPager: ViewPager2
    private lateinit var fragsList: ArrayList<Fragment>

    val match_info= MatchInfoFragment()
    val odds= OddsFragment()
    val h2h= H2HFragment()

    private val companyObjShared: SelectedCompanyObj? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)

        statusBarColor()
        casting()
        actionListenerToBack()
        fillAFragmentInArrayList()
        handleAViewPagerAdapter()
        tabLayoutController()
        if (MySharableObject.matchObject != null)
        {
            fillABasicInfo()
        }
    }

    private fun handleAViewPagerAdapter() {
        baseViewPager.adapter= ViewPagerAdapter(supportFragmentManager,lifecycle,fragsList)
        baseViewPager.isUserInputEnabled=false
        baseViewPager.offscreenPageLimit=2
    }

    private fun fillAFragmentInArrayList() {
        fragsList= ArrayList<Fragment>()
        fragsList.add(match_info)
        fragsList.add(odds)
        fragsList.add(h2h)
    }

    private fun tabLayoutController() {
        TabLayoutMediator(tabLayout,baseViewPager){tab,position->
            when(position){
                0->{
                    tab.text=getString(R.string.match_info)
                }
                1->{
                    tab.text=getString(R.string.odds)
                }
                2->{
                    tab.text=getString(R.string.h2h)
                }
            }
        }.attach()

        tabLayout.getTabAt(1)?.select();
    }

    private fun fillABasicInfo() {
        leagueNameShort.text = MySharableObject.matchObject?.leagueInfo?.enName
        home_team_name.text= MySharableObject.matchObject?.homeInfo?.enName

        away_team_name.text=MySharableObject.matchObject?.awayInfo?.enName

        var weather_number = MySharableObject.matchObject?.environment?.weather
        if (weather_number != null) {
             weather_status_txt.text = GeneralTools.getWeatherStatus(this, weather_number )
        }else{
            weather_status_txt.text = getString(R.string.weather_empty)
        }

        var match_score_str = MySharableObject.matchObject?.homeInfo?.homeScore.toString() + " - " + MySharableObject.matchObject?.awayInfo?.awayScore.toString()
        match_score.text = match_score_str


        val time_and_date:String = MySharableObject.matchObject?.matchTiming.toString()
        val list: List<String> = time_and_date.split(" ").toList()

        match_date.text = list.get(0)
        val mints_hours = list.get(1)
        val list2: List<String> = mints_hours.split(":").toList()
        match_time.text = list2.get(0) + ":" + list2.get(1)

        Glide.with(this).load(MySharableObject.matchObject?.homeInfo?.logo).into(home_image)
        Glide.with(this).load(MySharableObject.matchObject?.awayInfo?.logo).into(away_image)
        Glide.with(this).load(MySharableObject.matchObject?.leagueInfo?.logo).into(leagua_image)
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun casting() {
        tabLayout = findViewById<TabLayout>(R.id.tabLayoutMatchFragments)
        baseViewPager = findViewById<ViewPager2>(R.id.baseViewPagerMatchScreen)


        back_image = findViewById<ImageView>(R.id.back_image)
        leagueNameShort = findViewById<TextView>(R.id.group_indicator)

        home_team_name= findViewById<TextView>(R.id.home_team_name)
        away_team_name= findViewById<TextView>(R.id.away_team_name)
        match_score= findViewById<TextView>(R.id.match_score_txt)

        home_image= findViewById<ImageView>(R.id.home_team_image)
        away_image= findViewById<ImageView>(R.id.away_team_image)
        leagua_image= findViewById<ImageView>(R.id.leagua_image)

        weather_status_txt= findViewById<TextView>(R.id.wither_status_txt)

        weather_status_image= findViewById<ImageView>(R.id.wither_image)

        match_time= findViewById<TextView>(R.id.match_time)
        match_date= findViewById<TextView>(R.id.date_txt)
    }

    override fun onFill(company_obj: OddsCompanyComp) {
        Log.i("TAG","TAG onFill onActivity: "+ company_obj.name)
    }
}


