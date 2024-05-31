package com.example.view.allNews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.news.List
import com.example.model.news.NewsBase
import com.example.presnter.RecyclerViewOnclick
import com.example.view.mainActivity.homeAdapter.newsAdapter.AllNewsAdapter
import com.example.view.mainActivity.homeAdapter.newsAdapter.NewsAdapter_Horizontal
import com.example.view.newsDetails.NewsDetailsActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class AllNewsActivity : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var recycler_view: RecyclerView
    private lateinit var pro_bar_load_more: ProgressBar
    //pro_bar_load_more

    //value
    var pageNumber: Int = 1
    private lateinit var adapterAll: AllNewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    //server
    private lateinit var view_model: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_news)

        statusBarColor()
        casting()
        actionListenerToBack()
        checkIfTheRecyclerViewReachBottom()

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(this)
        //sent a requisite to get a banner ads
        view_model.getNews(pageNumber.toString(),"en")

        //observe banner response
        observeBannerResponse()
    }

    private fun checkIfTheRecyclerViewReachBottom() {
        // Add scroll listener to detect when the RecyclerView reaches the bottom
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    // RecyclerView can't scroll further down, hence at the bottom
                    onReachedBottom()
                }
            }
        })
    }


    private fun observeBannerResponse() {
        view_model.newsLiveData.observe(this){
            if (it.status== Status.SUCCESS){
                if (pageNumber == 1) {
                    populateRecyclerViewsHorizontal(it.data!!)
                }else{
                    updateAList(it.data!!)
                }
            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun updateAList(data: NewsBase) {
        pro_bar_load_more.isVisible = false
        // Scroll up by 100 pixels
        recycler_view.scrollBy(0, 100)
        adapterAll.updateList(data.list)
    }

    private fun onReachedBottom() {
        // Your code when RecyclerView reaches the bottom
        println("RecyclerView has reached the bottom.")
        pro_bar_load_more.isVisible = true
        pageNumber +=1
        view_model.getNews(pageNumber.toString(),"en")
    }

    private fun populateRecyclerViewsHorizontal(data: NewsBase) {
        val newsList= data?.list

        recycler_view.isNestedScrollingEnabled = false;

        adapterAll = AllNewsAdapter(this, newsList!! as ArrayList<List>,
            object : RecyclerViewOnclick {
                override fun onClick(position: Int) {
                    moveToNewsDetailsScreen(newsList[position].id)
                }
            })

        recycler_view.adapter= adapterAll

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler_view.layoutManager= linearLayoutManager


    }

    private fun moveToNewsDetailsScreen(id: Int?) {
        val intent = Intent(this, NewsDetailsActivity::class.java)
        // Put the string into the intent with a key
        intent.putExtra("NEWS_ID", id.toString())
        // Start ReceiverActivity
        startActivity(intent)
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
        back_image = findViewById<ImageView>(R.id.back_image)
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        pro_bar_load_more = findViewById<ProgressBar>(R.id.pro_bar_load_more)
    }
}