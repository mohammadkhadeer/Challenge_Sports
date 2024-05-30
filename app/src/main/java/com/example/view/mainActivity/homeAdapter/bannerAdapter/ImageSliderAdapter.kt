package com.example.view.mainActivity.homeAdapter.bannerAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.apisetup.notmodel.RetorfitBuilder
import com.example.model.banner.Top

class ImageSliderAdapter(var context: Context
                        , private val adsList: List<Top>
                        ,private val clickListener: OnItemClickListener
                        ) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.images_holder, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(RetorfitBuilder.adsBaseUrl + adsList[position].cover_path).into(holder.imageView)

        holder.imageView.setOnClickListener {
            Log.e("TAG","TAG adsList[position]: "+adsList[position])
            clickListener?.onItemClick(adsList[position])
        }
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    interface OnItemClickListener {
        fun onItemClick(adObj: Top)
    }

}
