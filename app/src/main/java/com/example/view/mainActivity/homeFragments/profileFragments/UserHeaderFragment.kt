package com.example.view.mainActivity.homeFragments.profileFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.editProfile.serverModel.UserData
import com.example.model.news.NewsBase
import com.example.presnter.RecyclerViewOnclick
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.utils.MySharableObjectViewModel
import com.example.view.allNews.AllNewsActivity
import com.example.view.login.Login
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.view.userProfileActivity.UserProfileActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class UserHeaderFragment : Fragment() {

    //ui
    private lateinit var user_name_txt           : TextView
    private lateinit var user_image              : ImageView
    private lateinit var following_number_text   : TextView
    private lateinit var follower_number_text    : TextView
    private lateinit var profile_info_txt        : TextView
    private lateinit var edit_profile_rl         : RelativeLayout

    //value
    private var fragmentContext: Context? = null

    //server
    private lateinit var view_model: MyViewModel

    //use it to can make a update when a come back from update activity
    private val REQUEST_CODE = 1

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
        return inflater.inflate(R.layout.user_header_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        actionListenerToEditProfile()

        //server
        if(MySharableObjectViewModel.viewModel != null)
        {
            view_model = MySharableObjectViewModel.viewModel!!
            view_model.getBasicInfoRequest()
            observeAResponse()
        }
    }

    private fun observeAResponse() {
        view_model.basicProfile.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                SharedPreferencesHelper.saveProfileInfo(requireActivity(), it.data!!.response.data)
                fillABasicInfoInTheFragment(it.data!!.response.data)
            }else{
                if (it.status == Status.ERROR){

                }

            }
        }
    }

    private fun fillABasicInfoInTheFragment(data: UserData) {
        Glide.with(this).load(data.profile_img).into(user_image)
        user_name_txt.text = data.name
        profile_info_txt.text      = SharedPreferencesHelper.getABio(requireContext())
        following_number_text.text = data.totalFolllowingCount.toString()
        follower_number_text.text  = data.totalFollowerCount.toString()
    }

    private fun actionListenerToEditProfile() {
        edit_profile_rl.setOnClickListener {
            moveToUserProfileScreen()
        }
    }

    private fun moveToUserProfileScreen() {
        val intent = Intent(requireActivity(), UserProfileActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }


    private fun casting(view: View) {
        user_name_txt         = view.findViewById(R.id.user_name_txt)
        following_number_text = view.findViewById(R.id.following_number_text)
        follower_number_text  = view.findViewById(R.id.follower_number_text)
        profile_info_txt      = view.findViewById(R.id.profile_info_txt)
        user_image            = view.findViewById(R.id.user_image)
        edit_profile_rl       = view.findViewById(R.id.edit_profile_rl)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        fillABasicInfoInTheFragment(SharedPreferencesHelper.getProfileInfo(requireActivity())!!)

    }

}