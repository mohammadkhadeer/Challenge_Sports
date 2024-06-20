package com.example.presnter

import android.widget.PopupWindow

interface SelectedCountryListener {
    fun onCountryPassed(countryName:String,popupWindow: PopupWindow)
}