package com.example.view.matchDetails.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.model.headToHeadMatches.MatchInfo
import com.example.utils.GeneralTools
import java.util.*

class AdapterH2HMatches (var context: Context, var h2hMatchesList:  List<MatchInfo>)
    : RecyclerView.Adapter<AdapterH2HMatches.AdapterH2HMatchesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterH2HMatches.AdapterH2HMatchesViewHolder {
        return AdapterH2HMatchesViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_h2h_matches,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterH2HMatches.AdapterH2HMatchesViewHolder, position: Int) {

        holder.homeTeamName.text = GeneralTools.getATextDependALanguage( h2hMatchesList[position].homeEnName, h2hMatchesList[position].homeCnName,context)
        holder.awayTeamName.text = GeneralTools.getATextDependALanguage( h2hMatchesList[position].awayEnName, h2hMatchesList[position].awayCnName,context)


        holder.score.text = "${h2hMatchesList[position].homeTeamScore.toString()}" + " - " + "${h2hMatchesList[position].awayTeamScore.toString()}"
        holder.timeTeamName.text = GeneralTools.convertTimeFormat(h2hMatchesList[position].matchTime.toString())
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class AdapterH2HMatchesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var homeTeamName: TextView = itemView.findViewById<TextView>(R.id.home_team_name_txt)
        var awayTeamName: TextView = itemView.findViewById<TextView>(R.id.away_team_name_txt)
        var score: TextView = itemView.findViewById<TextView>(R.id.score_txt)
        var timeTeamName: TextView = itemView.findViewById<TextView>(R.id.date_txt)
    }

    override fun getItemCount(): Int {
        return h2hMatchesList.size
    }
}