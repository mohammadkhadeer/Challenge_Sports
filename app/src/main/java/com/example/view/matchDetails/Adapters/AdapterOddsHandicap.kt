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
import kotlin.time.times

class AdapterOddsHandicap (var context: Context, var oddsList:  List<List<String>>)
    : RecyclerView.Adapter<AdapterOddsHandicap.AdapterOddsHandicapViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOddsHandicap.AdapterOddsHandicapViewHolder {
        return AdapterOddsHandicapViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_odds_handicap,parent,false))
    }


    override fun onBindViewHolder(holder: AdapterOddsHandicap.AdapterOddsHandicapViewHolder, position: Int) {

        var y : Double = oddsList[position][3].toDouble()
        var x:Double = -1 * y

        holder.home_txt_1.text = x.toString()
        holder.home_txt_2.text = oddsList[position][2]

        holder.away_txt_1.text = oddsList[position][3]
        holder.away_txt_2.text = oddsList[position][4]

        holder.odd_time_txt.text = GeneralTools.convertTimeFormat(oddsList[position][0])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class AdapterOddsHandicapViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var odd_time_txt = itemView.findViewById<TextView>(R.id.odd_time_txt_handicap)
        var home_txt_1 = itemView.findViewById<TextView>(R.id.home_txt_1)
        var home_txt_2 = itemView.findViewById<TextView>(R.id.home_txt_2)
        var away_txt_1 = itemView.findViewById<TextView>(R.id.away_txt_1)
        var away_txt_2 = itemView.findViewById<TextView>(R.id.away_txt_2)
    }

    override fun getItemCount(): Int {
        return oddsList.size
    }
}