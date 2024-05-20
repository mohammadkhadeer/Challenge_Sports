package com.example.presnter

import com.example.model.odds.OddsCompanyComp

interface RecyclerViewOnclickCompany {
    fun onClick(position:Int,company_obj: OddsCompanyComp)
}