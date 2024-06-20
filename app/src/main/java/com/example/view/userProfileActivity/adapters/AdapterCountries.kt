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
import com.example.presnter.RecyclerViewOnclickCountry

class AdapterCountries (var context: Context, var profileList:  ArrayList<String>, var onclick: RecyclerViewOnclickCountry)
    : RecyclerView.Adapter<AdapterCountries.AdapterOddsViewHolder>() {

    private var filteredItems: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCountries.AdapterOddsViewHolder {
        filteredItems = profileList
        return AdapterOddsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_countries,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterCountries.AdapterOddsViewHolder, position: Int) {
        holder.title_txt.text = profileList[position]

        if (profileList[position] == "Zimbabwe")
        {
            holder.sp_rl.isVisible = false
        }
    }

    fun filter(query: String) {
        var filteredList = ArrayList<String>()
        profileList = if (query.isEmpty()) {
            ArrayList()
        } else {
            filteredList = ArrayList<String>()
            for (item in filteredItems) {
                if (item.contains(query, ignoreCase = true)) {
                    filteredList.add(item)
                }
            }
            filteredList
        }
        profileList = filteredList
        notifyDataSetChanged()
    }

    fun refill(orginList: ArrayList<String>)
    {
        profileList = orginList
        notifyDataSetChanged()
    }

    inner class AdapterOddsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title_txt = itemView.findViewById<TextView>(R.id.title_txt)
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