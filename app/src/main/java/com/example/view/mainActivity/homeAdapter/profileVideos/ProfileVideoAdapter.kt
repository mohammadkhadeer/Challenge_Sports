package com.example.view.mainActivity.homeAdapter.profileVideos

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
import com.example.model.userVideos.Data
import com.example.presnter.NewsVideoRecyclerViewOnclick

class ProfileVideoAdapter (var context: Context,var videoList:List<Data> ):RecyclerView.Adapter<ProfileVideoAdapter.viewHolder>(){
    inner class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imageContainer: ImageView = itemView.findViewById<ImageView>(R.id.image_view)
        var number_of_viewa: TextView = itemView.findViewById<TextView>(R.id.text_view)
        init {
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(context).inflate(R.layout.profile_video_adapter,parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.number_of_viewa.text = videoList[position].views_count.toString()
        Glide.with(context).load(videoList?.get(position)?.thumbnail_url).into(holder.imageContainer)

        if (position == (videoList?.size?.minus(1) ?: false)){
            //loadMoreCommunicator.loadMore()
        }
    }


    override fun getItemCount(): Int {
        return videoList.size
    }

}

