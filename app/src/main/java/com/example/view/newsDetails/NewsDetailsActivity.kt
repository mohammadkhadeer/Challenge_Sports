package com.example.view.newsDetails

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.apisetup.notmodel.Status
import com.example.model.news.details.NewsPostBase
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout

class NewsDetailsActivity : AppCompatActivity() {
    //ui
    private lateinit var back_image: ImageView
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    //value
    private lateinit var news_id:String

    //server
    private lateinit var view_model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        getANewsIdFromPastActivity()
        //ui
        casting()
        statusBarColor()
        actionListenerToBack()

        //handle server object
        view_model = SpewViewModel.giveMeViewModel(this)
        //sent a requisite to get a banner ads
        view_model.getANewsDetails(news_id,"en")

        //observe banner response
        observeBannerResponse()
    }

    private fun observeBannerResponse() {
        view_model.newsDetailsData.observe(this){
            if (it.status== Status.SUCCESS){
                println(it.data)

                fillAData(it.data!!)
            }else{
                if (it.status == Status.ERROR){
                    Log.i("TAG" ,"it.message "+it.message)
                }
            }
        }
    }

    private fun fillAData(data: NewsPostBase) {
        Glide.with(this).load(data.path).into(imageView)

        // Convert HTML string to Spanned
        val spannedContent: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(data.content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(data.content)
        }

        // Set the Spanned content to the TextView
        textView.text = spannedContent
        // Enable clickable links
        textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
//        textView.text = data.content
    }

    private fun actionListenerToBack() {
        back_image.setOnClickListener{
            finish()
        }
    }
    private fun getANewsIdFromPastActivity() {
        news_id = intent.getStringExtra("NEWS_ID")!!
    }

    private fun casting() {
        back_image = findViewById<ImageView>(R.id.back_image)
        imageView = findViewById<ImageView>(R.id.imageView)
        textView = findViewById<TextView>(R.id.textView)
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}