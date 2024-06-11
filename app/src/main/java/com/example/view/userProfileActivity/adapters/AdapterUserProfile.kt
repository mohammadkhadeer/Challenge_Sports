package com.example.view.userProfileActivity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.model.editProfile.EditProfileInfo
import com.example.presnter.RecyclerViewOnclickProfile

class AdapterUserProfile (var context: Context, var profileList:  List<EditProfileInfo>, var onclick: RecyclerViewOnclickProfile)
    : RecyclerView.Adapter<AdapterUserProfile.AdapterOddsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUserProfile.AdapterOddsViewHolder {
        return AdapterOddsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_user_profile,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterUserProfile.AdapterOddsViewHolder, position: Int) {
        holder.title_txt.text = profileList[position].title
        holder.title_content_txt.text = profileList[position].contentTxt
        if (profileList[position].title == context.getString(R.string.password))
        {
            holder.sp_rl.isVisible = false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class AdapterOddsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title_txt = itemView.findViewById<TextView>(R.id.title_txt)
        var title_content_txt = itemView.findViewById<TextView>(R.id.title_content_txt)
        var sp_rl = itemView.findViewById<RelativeLayout>(R.id.sp_rl)
//
        init {
            itemView.setOnClickListener {
                onclick.onClick(absoluteAdapterPosition, profileList[absoluteAdapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return profileList.size
    }
}