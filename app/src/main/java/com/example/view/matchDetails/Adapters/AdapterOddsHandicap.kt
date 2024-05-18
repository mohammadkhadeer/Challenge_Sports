package com.example.view.matchDetails.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.model.odds.Oddlist
import java.sql.DriverManager.println
import java.text.SimpleDateFormat
import java.util.*

class AdapterOddsHandicap (var context: Context, var oddsList:  List<Oddlist>)
    : RecyclerView.Adapter<AdapterOddsHandicap.AdapterOddsHandicapViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOddsHandicap.AdapterOddsHandicapViewHolder {
        return AdapterOddsHandicapViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_odds_handicap,parent,false))
    }


    override fun onBindViewHolder(holder: AdapterOddsHandicap.AdapterOddsHandicapViewHolder, position: Int) {

        holder.odd_time_txt.text = oddsList.get(0).odds[position][0]
        holder.home_txt_1.text = oddsList.get(0).odds[position][2]
        holder.home_txt_2.text = oddsList.get(0).odds[position][2]
        holder.away_txt_1.text = oddsList.get(0).odds[position][4]
        holder.away_txt_2.text = oddsList.get(0).odds[position][4]

        //make a method in general
        var intValue = oddsList.get(0).odds[position][0]
        val number: Int? = intValue.toIntOrNull()
        val dateFormatter = SimpleDateFormat("h:mm a EEEE, MMMM dd, yyyy", Locale.getDefault())
        val date = Date(number?.times(1000L) ?: 0)  // Kotlin uses milliseconds for the Date constructor

        val dateString = dateFormatter.format(date)
        println("Human-readable date: $dateString")
        holder.odd_time_txt.text = dateString
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
        return oddsList.get(0).odds.size
    }
}