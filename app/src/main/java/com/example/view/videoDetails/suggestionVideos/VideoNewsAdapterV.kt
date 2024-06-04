package com.example.view.videoDetails.suggestionVideos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.model.newsVideo.VideoList
import com.example.presnter.NewsVideoRecyclerViewOnclick

class VideoNewsAdapterV (var context: Context, private var  videoList:ArrayList<VideoList>
                         , var onclick: NewsVideoRecyclerViewOnclick
                                 ):RecyclerView.Adapter<VideoNewsAdapterV.viewHolder>(){
    inner class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imageContainer: ImageView = itemView.findViewById<ImageView>(R.id.image_view_news)
        var timeAgo: TextView = itemView.findViewById<TextView>(R.id.time)
        var news_text: TextView = itemView.findViewById<TextView>(R.id.news_text)
        init {
            itemView.setOnClickListener{
                onclick.onClick(videoList[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.video_news_rv_element_v,parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.timeAgo.text   = videoList[position].createTime?.substringBefore("T")
        holder.news_text.text = videoList[position].title
        Glide.with(context).load(videoList?.get(position)?.thumbnailPath).into(holder.imageContainer)

        if (position == (videoList?.size?.minus(1) ?: false)){
            //loadMoreCommunicator.loadMore()
        }
    }


    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateList(list:List<VideoList>){
        videoList.addAll(list)
        notifyDataSetChanged()
    }
}

