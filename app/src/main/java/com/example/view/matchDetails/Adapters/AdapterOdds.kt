package com.example.view.matchDetails.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.model.odds.Oddlist
import com.example.utils.GeneralTools
import java.sql.DriverManager.println
import java.text.SimpleDateFormat
import java.util.*

class AdapterOdds (var context: Context, var oddsList:  List<List<String>>)
    : RecyclerView.Adapter<AdapterOdds.AdapterOddsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOdds.AdapterOddsViewHolder {
        return AdapterOddsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_odds,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterOdds.AdapterOddsViewHolder, position: Int) {

        holder.home_txt.text = oddsList[position][0]
        holder.vs_x_txt.text = oddsList[position][2]
        holder.away_txt.text = oddsList[position][4]

        holder.odd_time_txt.text = GeneralTools.convertTimeFormat(oddsList[position][0])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class AdapterOddsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var odd_time_txt = itemView.findViewById<TextView>(R.id.odd_time_txt)
        var home_txt = itemView.findViewById<TextView>(R.id.home_txt)
        var vs_x_txt = itemView.findViewById<TextView>(R.id.vs_x_txt)
        var away_txt = itemView.findViewById<TextView>(R.id.away_txt)
    }

    override fun getItemCount(): Int {
        return oddsList.size
    }
}