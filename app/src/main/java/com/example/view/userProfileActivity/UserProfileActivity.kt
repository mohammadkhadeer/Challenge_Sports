package com.example.view.userProfileActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.model.editProfile.EditProfileInfo
import com.example.model.news.List
import com.example.model.odds.OddsCompanyComp
import com.example.presnter.LanguageBottomSheetListener
import com.example.presnter.RecyclerViewOnclick
import com.example.presnter.RecyclerViewOnclickCompany
import com.example.presnter.RecyclerViewOnclickProfile
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.utils.GeneralTools
import com.example.view.bottomSheet.ForgotBottomSheetFragment
import com.example.view.bottomSheet.SelectLanguageBottomSheetFragment
import com.example.view.mainActivity.MainActivity
import com.example.view.mainActivity.homeAdapter.newsAdapter.AllNewsAdapter
import com.example.view.userProfileActivity.adapters.AdapterUserProfile
import java.util.ArrayList

class UserProfileActivity : AppCompatActivity() , LanguageBottomSheetListener {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var bio_info_ll: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var language_ll: LinearLayout
    private lateinit var privacy_policy_ll: LinearLayout
    private lateinit var terms_and_conditions_ll: LinearLayout
    private lateinit var sign_out_ll: LinearLayout
    private lateinit var selected_language_txt: TextView

    //values
    private lateinit var adapter: AdapterUserProfile
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var profile_list: ArrayList<EditProfileInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        statusBarColor()
        casting()
        actionListenerToBack()
        actionListenerToBio()
        //all user comp listener in-said a recyclerView listener
        handleARecyclerView()
        actionListenerToLanguage()
        actionListenerToPrivacyPolicy()
        actionListenerToTermsAndConditions()
        actionListenerToSignOut()
    }

    private fun actionListenerToSignOut() {
        sign_out_ll.setOnClickListener{
            SharedPreferencesHelper.clearData(this)
            moveToNextActivity()
        }
    }

    private fun moveToNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
    private fun actionListenerToTermsAndConditions() {
        terms_and_conditions_ll.setOnClickListener{
            Toast.makeText(this, "terms_and_conditions_ll clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actionListenerToPrivacyPolicy() {
        privacy_policy_ll.setOnClickListener{
            Toast.makeText(this, "privacy_policy_ll clicked!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun actionListenerToLanguage() {
        language_ll.setOnClickListener{
            val modalBottomSheet = SelectLanguageBottomSheetFragment()
            modalBottomSheet.listener = this
            modalBottomSheet.show(supportFragmentManager, SelectLanguageBottomSheetFragment::class.java.simpleName)
        }
    }

    private fun handleARecyclerView() {
        recyclerView.isNestedScrollingEnabled = false

        //fill profile list
        profile_list = EditProfileTools.fillProfileList(this)

        adapter = AdapterUserProfile(this,
            profile_list!!
            , object : RecyclerViewOnclickProfile {
                override fun onClick(position: Int, profile_obj: EditProfileInfo) {
                    println( profile_obj.contentTxt)
                }

            })

        recyclerView.adapter= adapter

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager= linearLayoutManager
    }

    private fun actionListenerToBio() {
        bio_info_ll.setOnClickListener{
            Toast.makeText(this, "LinearLayout clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            finish()
        }
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun casting() {
        back_image               = findViewById<ImageView>(R.id.back_image)
        bio_info_ll              = findViewById<LinearLayout>(R.id.bio_info_ll)
        recyclerView             = findViewById<RecyclerView>(R.id.recyclerView)
        language_ll              = findViewById<LinearLayout>(R.id.language_ll)
        privacy_policy_ll        = findViewById<LinearLayout>(R.id.privacy_policy_ll)
        terms_and_conditions_ll  = findViewById<LinearLayout>(R.id.terms_and_conditions_ll)
        sign_out_ll              = findViewById<LinearLayout>(R.id.sign_out_ll)
        selected_language_txt    = findViewById<TextView>(R.id.selected_language_txt)
    }

    override fun onDataPassed(data: String) {
        selected_language_txt.text = data
    }
}