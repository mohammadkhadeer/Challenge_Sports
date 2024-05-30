package com.example.view.mainActivity.homeAdapter.newsAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.presnter.RecyclerViewOnclick

class NewsAdapter_Horizontal (var context: Context, private var  newsList:ArrayList<com.example.model.news.List>
                              , var onclick: RecyclerViewOnclick):RecyclerView.Adapter<NewsAdapter_Horizontal.viewHolder>(){
    inner class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imageContainer: ImageView = itemView.findViewById<ImageView>(R.id.image_view_news)
        var timeAgo: TextView = itemView.findViewById<TextView>(R.id.time)
        var news_text: TextView = itemView.findViewById<TextView>(R.id.news_text)
        init {
            itemView.setOnClickListener{
                onclick.onClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.news_rv_element_horizontal,parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.timeAgo.text   = newsList[position].createTime?.substringBefore("T")
        holder.news_text.text = newsList[position].title
        Glide.with(context).load(newsList?.get(position)?.path).into(holder.imageContainer)

        if (position == (newsList?.size?.minus(1) ?: false)){
            //loadMoreCommunicator.loadMore()
        }
    }


    override fun getItemCount(): Int {
        return newsList.size
    }

}

