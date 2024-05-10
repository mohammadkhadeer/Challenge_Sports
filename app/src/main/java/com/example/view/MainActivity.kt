package com.example.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apisetup.R
import com.example.apisetup.notmodel.ApiImpl
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vm=SpewViewModel.giveMeViewModel(this)
        vm.makeCallAPI()

    }
}