package com.example.view.matchDetails.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.model.odds.OddsCompanyComp
import com.example.presnter.RecyclerViewOnclickCompany

class AdapterCompanyList (var context: Context,  var companyList:  List<OddsCompanyComp>, var onclick: RecyclerViewOnclickCompany)
    : RecyclerView.Adapter<AdapterCompanyList.AdapterCompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCompanyList.AdapterCompanyViewHolder {
        return AdapterCompanyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_company_list,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterCompanyList.AdapterCompanyViewHolder, position: Int) {
        holder.company_name_txt.text = companyList.get(position).name
        Glide.with(context).load(companyList.get(position).image_path).into(holder.company_image_view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class AdapterCompanyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var company_name_txt = itemView.findViewById<TextView>(R.id.company_name_txt)
        var company_image_view = itemView.findViewById<ImageView>(R.id.company_image_view)

        init {
            itemView.setOnClickListener {
                onclick.onClick(absoluteAdapterPosition, companyList.get(position)!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return companyList.size
    }
}