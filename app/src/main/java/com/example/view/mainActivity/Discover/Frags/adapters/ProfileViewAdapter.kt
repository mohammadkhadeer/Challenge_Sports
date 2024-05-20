package com.example.view.mainActivity.Discover.Frags.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.view.mainActivity.Discover.Frags.adapters.containerModel.ProfileFragRvDataClass

class ProfileViewAdapter (private val items: List<ProfileFragRvDataClass>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    companion object {
        const val TYPE_PROFILE = 2
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }



    override fun getItemViewType(position: Int): Int {
        return when (position) {
            1 -> {
                TYPE_HEADER
            }
            0 -> {
                TYPE_PROFILE
            }
            else -> {
                TYPE_ITEM
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.sticky_header_profile, parent, false)

                HeaderViewHolder(view)
            }
            TYPE_PROFILE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_header_item, parent, false)
                ProfileViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.videos_thumbnail_item, parent, false)
                ItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> {
                holder.bind(items[position])
            }

            is HeaderViewHolder -> {

                holder.bind(items[position])
            }

            is ItemViewHolder -> {
                holder.bind((items[2].cellData as List<*>)[position-2] as String)
            }
        }
    }


    override fun getItemCount(): Int = (items[2].cellData as List<*>).size + 2

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ProfileFragRvDataClass) {

        }
    }
    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ProfileFragRvDataClass) {

        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {

        }
    }
}
