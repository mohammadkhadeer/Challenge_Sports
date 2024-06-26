package com.example.view.mainActivity.homeFragments.homeFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.news.NewsBase
import com.example.presnter.RecyclerViewOnclick
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.view.allNews.AllNewsActivity
import com.example.view.mainActivity.homeAdapter.bannerAdapter.ImageSliderAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class HorzNewsFragment : Fragment() {

    //ui
    private lateinit var horizontal_rv: RecyclerView
    private lateinit var pro_bar: ProgressBar
    private lateinit var see_all_txt: TextView

    //value
    private var fragmentContext: Context? = null

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
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        actionListenerToSeeAll()
        //handle server object
        view_model = SpewViewModel.giveMeViewModel(requireActivity())
        //sent a requisite to get a banner ads
        var lang = SharedPreferencesHelper.getALanguage(requireContext())!!
        if (lang == "zh")
            lang = "cn"
        view_model.getNews("1", lang)

        //observe banner response
        observeBannerResponse()
    }

    private fun actionListenerToSeeAll() {
        see_all_txt.setOnClickListener {
            val intent = Intent(requireActivity(), AllNewsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeBannerResponse() {
        view_model.newsLiveData.observe(this){
            if (it.status== Status.SUCCESS){
                if (fragmentContext != null){
                    pro_bar.isVisible = false

                    populateRecyclerViewsHorizontal(it.data!!)
                }

            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun populateRecyclerViewsHorizontal(data: NewsBase) {
        val newsList= data?.list

        horizontal_rv.isNestedScrollingEnabled = false;

        horizontal_rv.adapter= NewsAdapter_Horizontal(requireContext(), newsList!! as ArrayList<com.example.model.news.List>,
            object : RecyclerViewOnclick {
                override fun onClick(position: Int) {
                    moveToNewsDetailsScreen(newsList[position].id)
                }
            })

        horizontal_rv.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

    }

    private fun moveToNewsDetailsScreen(id: Int?) {
        val intent = Intent(requireActivity(), NewsDetailsActivity::class.java)
        // Put the string into the intent with a key
        intent.putExtra("NEWS_ID", id.toString())
        // Start ReceiverActivity
        startActivity(intent)
    }

    private fun casting(view: View) {
        horizontal_rv= view.findViewById<RecyclerView>(R.id.horizontal_rv)
        pro_bar= view.findViewById<ProgressBar>(R.id.pro_bar)
        see_all_txt= view.findViewById<TextView>(R.id.see_all_txt)
    }

}