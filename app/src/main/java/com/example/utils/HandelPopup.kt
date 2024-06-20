package com.example.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.presnter.RecyclerViewOnclickCountry
import com.example.view.userProfileActivity.adapters.AdapterCountries

object HandelPopup {
    fun handelPopup(view: View,context: Context,view_per:View) {
        // Create the PopupWindow
        val popupWindow = PopupWindow(view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true)

        popupWindow.update(0, 200, LinearLayout.LayoutParams.WRAP_CONTENT, 1400)

        handelACountriesRV(view,context)
        openKeyboard(view,context)
        // Set up the close button inside the popup
        val closeButton: Button = view.findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }

        // show the popup window main
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view_per, Gravity.CENTER_HORIZONTAL, 0, 100)

    }
    private fun openKeyboard(view: View, context: Context) {
        val editText: EditText = view.findViewById(R.id.editText)

        // Request focus and show keyboard
        editText.isFocusableInTouchMode = true
        editText.requestFocus()

        Handler(Looper.getMainLooper()).postDelayed({
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }, 100)
    }


    private fun handelACountriesRV(view: View, context: Context) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.isNestedScrollingEnabled = false
        var countriesList: ArrayList<String> = ArrayList()

        val countriesArray = context.resources.getStringArray(R.array.countries_array)
        countriesList = countriesArray.toMutableList() as ArrayList<String>
        println(countriesArray.size)

        var adapter = AdapterCountries(context,
            countriesList!!
            , object : RecyclerViewOnclickCountry {
                override fun onClick(position: Int, countryName: String) {

                    println(countryName)

                }

            })
        recyclerView.adapter = adapter

        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager= linearLayoutManager

        val editText: EditText = view.findViewById(R.id.editText)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}