package com.example.view.userProfileActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.apisetup.notmodel.Status
import com.example.model.editProfile.EditProfileInfo
import com.example.presnter.GenderSheetListener
import com.example.presnter.LanguageBottomSheetListener
import com.example.presnter.RecyclerViewOnclickProfile
import com.example.presnter.SelectedCountryListener
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.utils.HandelPopup
import com.example.view.bottomSheet.updateProfileBottomSheet.SelectGenderBottomSheetFragment
import com.example.view.bottomSheet.updateProfileBottomSheet.SelectLanguageBottomSheetFragment
import com.example.view.mainActivity.MainActivity
import com.example.view.updateBirthday.UpdateBirthdayActivity
import com.example.view.updateUserInfo.UpdateUserInfoActivity
import com.example.view.userProfileActivity.adapters.AdapterUserProfile
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class UserProfileActivity : AppCompatActivity() , LanguageBottomSheetListener ,GenderSheetListener ,SelectedCountryListener{
    //ui
    private lateinit var back_image: ImageView
    private lateinit var bio_info_ll: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var language_ll: LinearLayout
    private lateinit var privacy_policy_ll: LinearLayout
    private lateinit var terms_and_conditions_ll: LinearLayout
    private lateinit var sign_out_ll: LinearLayout
    private lateinit var selected_language_txt: TextView
    private lateinit var bio_content_txt: TextView

    //popupWindow: PopupWindow
    //values
    private lateinit var adapter: AdapterUserProfile
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var profile_list: ArrayList<EditProfileInfo> = ArrayList()
    private lateinit var popupWindowInActivity: PopupWindow

    //call back
    //use it to can make a update when a come back from update activity
    private val REQUEST_CODE = 1
    private val REQUEST_CODE_BIO = 2

    //server
    private lateinit var view_model: MyViewModel

    //interface
    private var selectedCountryListener:SelectedCountryListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        statusBarColor()
        casting()
        actionListenerToBack()
        fillBioText()
        actionListenerToBio()

        //all user comp listener in-said a recyclerView listener at line 125-130
        handleARecyclerView()
        actionListenerToLanguage()
        actionListenerToPrivacyPolicy()
        actionListenerToTermsAndConditions()
        actionListenerToSignOut()

        view_model = SpewViewModel.giveMeViewModelWithHeader(this)
        observeAResponse()
    }

    private fun observeAResponse() {
        view_model.updateBasicInfo.observe(this){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                title = getString(R.string.country)
                Toast.makeText(this, getString(R.string.update_successfully_message1) +" "+ title + " "+getString(R.string.update_successfully_message2), Toast.LENGTH_SHORT).show()
                Log.i("TAG","myData "+ it.data!!)
                SharedPreferencesHelper.saveProfileInfo(this, it.data!!.response.data)

                handleARecyclerView()
                popupWindowInActivity.dismiss()
            }else{
                if (it.status == Status.ERROR){
                    //allow to user to try again
                    Toast.makeText(this, getString(R.string.error_message_1), Toast.LENGTH_SHORT).show()
                    popupWindowInActivity.dismiss()
                }

            }
        }
    }

    private fun fillBioText() {
        if (SharedPreferencesHelper.getABio(this) != null)
            bio_content_txt.text = SharedPreferencesHelper.getABio(this)
        else
            bio_content_txt.text = getString(R.string.profile_activity_message2)
    }

    private fun actionListenerToSignOut() {
        sign_out_ll.setOnClickListener{
            SharedPreferencesHelper.clearData(this)
            SharedPreferencesHelper.clearProfileInfo(this)

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
            moveToBrowser(RetorfitBuilder.termsAndConditions)
        }
    }

    private fun actionListenerToPrivacyPolicy() {
        privacy_policy_ll.setOnClickListener{
            moveToBrowser(RetorfitBuilder.privacyPolicy)
        }
    }

    private fun moveToBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
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

        profile_list = ArrayList()
        profile_list = EditProfileTools.fillProfileList(this)

        adapter = AdapterUserProfile(this,
            profile_list!!
            , object : RecyclerViewOnclickProfile {
                override fun onClick(position: Int, profile_obj: EditProfileInfo) {
                    handleActionListener(profile_obj)
                }
            })
        recyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager= linearLayoutManager
    }

    private fun handleActionListener(profileObj: EditProfileInfo) {
        when (profileObj.title) {
            getString(R.string.full_name) ->
                moveToUpdateUserInfo(profileObj)
            getString(R.string.email) ->
                notAllowedToChangeEmailMessage()
            getString(R.string.birthday) ->
                moveToUpdateBirthday(profileObj)
            getString(R.string.gender) ->
                moveToUpdateGender(profileObj)
            getString(R.string.location) ->
                showPopup()
            getString(R.string.password) ->
                notAllowedToChangeEmailMessage()
        }
    }


    private fun notAllowedToChangeEmailMessage() {
        Toast.makeText(this, getString(R.string.profile_activity_message), Toast.LENGTH_SHORT).show()
    }

    private fun moveToUpdateUserInfo(profileObj: EditProfileInfo) {
        val intent = Intent(this, UpdateUserInfoActivity::class.java).apply {
            putExtra("title", profileObj.title)
            putExtra("contentTxt", profileObj.contentTxt)
            putExtra("server_key", profileObj.value_in_server)
        }
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun moveToUpdateBirthday(profileObj: EditProfileInfo) {
        val intent = Intent(this, UpdateBirthdayActivity::class.java).apply {
            putExtra("title", profileObj.title)
            putExtra("contentTxt", profileObj.contentTxt)
            putExtra("server_key", profileObj.value_in_server)
        }
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun moveToUpdateGender(profileObj: EditProfileInfo) {
        val modalBottomSheet = SelectGenderBottomSheetFragment()
        modalBottomSheet.listener = this
        modalBottomSheet.show(supportFragmentManager, SelectGenderBottomSheetFragment::class.java.simpleName)
    }

    private fun moveToUpdateBio(profileObj: EditProfileInfo) {
        val intent = Intent(this, UpdateUserInfoActivity::class.java).apply {
            putExtra("title", profileObj.title)
            putExtra("contentTxt", profileObj.contentTxt)
            putExtra("server_key", profileObj.value_in_server)
        }
        startActivityForResult(intent, REQUEST_CODE_BIO)
//        startActivity(intent)
    }

    private fun actionListenerToBio() {
        bio_info_ll.setOnClickListener{
            var bio = getString(R.string.profile_activity_message2)
            if (SharedPreferencesHelper.getABio(this) != null)
                bio = SharedPreferencesHelper.getABio(this)!!

            moveToUpdateBio(EditProfileInfo(getString(R.string.bio),bio,"about"))
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
        bio_content_txt          = findViewById<TextView>(R.id.bio_content_txt)
    }


    @SuppressLint("ResourceType")
    private fun showPopup() {
        // Inflate the popup_layout.xml
        val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout, null)

        val view_per: View = findViewById(R.id.popup_view)

        //create instance from a pass country interface
        this.selectedCountryListener = this@UserProfileActivity as SelectedCountryListener

        HandelPopup.handelPopup(popupView,this,view_per,selectedCountryListener)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringExtra("result")

            handleARecyclerView()
        }

        if (requestCode == REQUEST_CODE_BIO && resultCode == RESULT_OK) {
            fillBioText()
        }
    }

    override fun onDataPassed(data: String) {
        selected_language_txt.text = data
    }

    override fun onGenderPassed() {
        handleARecyclerView()
    }

    override fun onCountryPassed(countryName: String,popupWindow: PopupWindow) {
        popupWindowInActivity = popupWindow
        val map = EditProfileTools.makeMapForUpdateNameRequirements(countryName,"country")
        view_model.updateBasicInfoRequest(map)
    }
}