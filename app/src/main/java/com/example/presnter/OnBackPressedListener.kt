package com.example.presnter

import androidx.fragment.app.Fragment

interface OnBackPressedListener {
    fun changeBackPressBehaviour(currentFragment: Fragment)
    fun changeBackPressBehaviour(currentFragment: Fragment,message:String)
}