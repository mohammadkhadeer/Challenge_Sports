package com.example.view.mainActivity.homeAdapter.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.hotMatches.HotMatche
import com.example.utils.GeneralTools
import java.sql.DriverManager.println
import java.util.*

class MatchesAdapter (var context: Context
, var dataList: List<HotMatche?>)
    : RecyclerView.Adapter<MatchesAdapter.MainAdapterViewHolder>(),
    Filterable {

    private var originalList = dataList

    var listMatches= ArrayList<HotMatche>()

    init {
        sortMatchesOnCategory()
    }

    private fun sortMatchesOnCategory(){
        listMatches.clear()

        for (match in originalList!!){
            listMatches.add(match!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MainAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var leagueNameShort=itemView.findViewById<TextView>(R.id.group_indicator)

        var home_team_name=itemView.findViewById<TextView>(R.id.home_team_name)
        var away_team_name=itemView.findViewById<TextView>(R.id.away_team_name)
        var match_score=itemView.findViewById<TextView>(R.id.match_score_txt)

        var home_image=itemView.findViewById<ImageView>(R.id.home_team_image)
        var away_image=itemView.findViewById<ImageView>(R.id.away_team_image)
        var leagua_image=itemView.findViewById<ImageView>(R.id.leagua_image)

        var weather_status_txt=itemView.findViewById<TextView>(R.id.wither_status_txt)

        var weather_status_image=itemView.findViewById<ImageView>(R.id.wither_image)

        var match_time=itemView.findViewById<TextView>(R.id.match_time)
        var match_date=itemView.findViewById<TextView>(R.id.date_txt)



        var fragment_container=itemView.findViewById<FrameLayout>(R.id.fragment_container)
        init {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        return MainAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_matches,parent,false))
    }

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {

        val dataObject = dataList.get(position)

        holder.leagueNameShort.text = dataObject?.leagueInfo?.enName
        holder.home_team_name.text= dataObject?.homeInfo?.enName

        holder.away_team_name.text=dataObject?.awayInfo?.enName

        var weather_number = dataObject?.environment?.weather
        if (weather_number != null) {
            holder.weather_status_txt.text = GeneralTools.getWeatherStatus(context, weather_number )
        }else{
            holder.weather_status_txt.text = context.getString(R.string.weather_empty)
        }

        var match_score = dataObject?.homeInfo?.homeScore.toString() + " - " + dataObject?.awayInfo?.awayScore.toString()
        holder.match_score.text = match_score


        val time_and_date:String = dataObject?.matchTiming.toString()
        val list: List<String> = time_and_date.split(" ").toList()
        println(list)
        holder.match_date.text = list.get(0)
        val mints_hours = list.get(1)
        val list2: List<String> = mints_hours.split(":").toList()
        holder.match_time.text = list2.get(0) + ":" + list2.get(1)

        Glide.with(context).load(dataObject?.homeInfo?.logo).into(holder.home_image)
        Glide.with(context).load(dataObject?.awayInfo?.logo).into(holder.away_image)
        Glide.with(context).load(dataObject?.leagueInfo?.logo).into(holder.leagua_image)


//        if (position==dataList.size-1&&loadMore)
//            communicator.onMessageFromAdapter(MainAdapterMessages.LOAD_MORE,position,0)
    }


    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun getFilter(): Filter? {
        //TODO("Not yet implemented")
        return null
    }


}