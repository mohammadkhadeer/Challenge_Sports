package com.example.view.mainActivity.homeAdapter.matches

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.hotMatches.HotMatche
import java.util.*

class MatchesAdapter (var context: Context
, var dataList: HotMatchBaseClass)
    : RecyclerView.Adapter<MatchesAdapter.MainAdapterViewHolder>(),
    Filterable {

    var originalList = dataList.hotMatches
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

        Log.i("TAG","dataList.hotMatches "+ (dataList.hotMatches?.size ?: 10))
        Log.i("TAG","dataList.hotMatches!!.get(position) "+ dataList.hotMatches!!.get(position)!!.id)

        val dataObject = dataList.hotMatches!!.get(position)

        holder.leagueNameShort.text = "mohamamd"

        Log.i("TAG","dataObject?.homeInfo "+ dataObject?.homeInfo?.enName)

        holder.home_team_name.text= dataObject?.homeInfo?.enName

        holder.away_team_name.text=dataObject?.awayInfo?.enName

        Glide.with(context).load(dataObject?.homeInfo?.logo).into(holder.home_image)
        Glide.with(context).load(dataObject?.awayInfo?.logo).into(holder.away_image)


//        if (position==dataList.size-1&&loadMore)
//            communicator.onMessageFromAdapter(MainAdapterMessages.LOAD_MORE,position,0)
    }


    override fun getItemCount(): Int {
        return dataList.hotMatches?.size ?: 0
    }

    override fun getFilter(): Filter? {
        //TODO("Not yet implemented")
        return null
    }


}