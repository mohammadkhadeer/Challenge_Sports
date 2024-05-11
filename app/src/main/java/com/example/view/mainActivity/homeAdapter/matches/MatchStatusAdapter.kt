package com.example.view.mainActivity.homeAdapter.matches

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.model.hotMatches.MatchStatusJ
import com.example.presnter.RecyclerViewOnclick
import java.util.*


class MatchStatusAdapter(var context: Context, var dataList: ArrayList<MatchStatusJ>
, var onclick: RecyclerViewOnclick
)
    : RecyclerView.Adapter<MatchStatusAdapter.MatchStatusAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchStatusAdapterViewHolder {
        return MatchStatusAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.match_status_adapter,parent,false))
    }


    override fun onBindViewHolder(holder: MatchStatusAdapterViewHolder, position: Int) {
        holder.match_status_txv.text = dataList[position].name

        if (dataList[position].selected)
        {
            holder.container_match_status_rl.setBackgroundResource(R.drawable.matches_status_selected_bg)
            holder.match_status_txv.setTextColor(ContextCompat.getColor(context, R.color.white))
        }else{
            holder.container_match_status_rl.setBackgroundResource(R.drawable.matches_status_bg)
            holder.match_status_txv.setTextColor(ContextCompat.getColor(context, R.color.splash_screen_gradint_2))
        }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MatchStatusAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var match_status_txv = itemView.findViewById<TextView>(R.id.status_txv)
        var container_match_status_rl = itemView.findViewById<RelativeLayout>(R.id.container_match_status_rl)

        init {
            itemView.setOnClickListener {
                //changeSelectedBackGround(match_status_txv,container_match_status_rl,position)
                onclick.onClick(absoluteAdapterPosition)
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}