package com.example.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.odds.OddsCompanyComp

class SelectedCompanyObj : ViewModel() {
    private val _selectedValue = MutableLiveData<OddsCompanyComp>()
    val selectedValue: LiveData<OddsCompanyComp> get() = _selectedValue

    fun selectValue(value: OddsCompanyComp) {
        _selectedValue.value = value
    }
}