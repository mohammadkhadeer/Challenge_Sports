package com.example.view.bottomSheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.databinding.FragmentModalBottomSheetBinding
import com.example.apisetup.notmodel.Status
import com.example.model.odds.OddsCompanyComp
import com.example.presnter.LanguageBottomSheetListener
import com.example.presnter.RecyclerViewOnclickCompany
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.GeneralTools
import com.example.utils.RegisterTools
import com.example.utils.SelectedCompanyObj
import com.example.view.matchDetails.Adapters.AdapterCompanyList
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class SelectLanguageBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentModalBottomSheetBinding? = null
    //ui
    private lateinit var radioGroup: RadioGroup
    private lateinit var view_global: View

    //value
    private var selectedLanguage: String = ""
    private val handler = Handler(Looper.getMainLooper())

    //interface
    var listener: LanguageBottomSheetListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        selectedLanguage = SharedPreferencesHelper.getALanguage(requireContext()).toString()
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Apply the custom style here
        return BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.language_select_bottom_sheet, container, false)
        casting(view)
        view_global = view

        fillARadioButton()
        return view
    }

    private fun fillARadioButton() {
        if (selectedLanguage != null && selectedLanguage == "ENGLISH")
        {
            radioGroup.check(R.id.radioButton1)
        }

        if (selectedLanguage != null && selectedLanguage == "CHINES")
        {
            radioGroup.check(R.id.radioButton2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionListenerToRadioButton()
    }

    private fun actionListenerToRadioButton() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view_global.findViewById(checkedId)
            if (radioButton.text == "English")
            {
                SharedPreferencesHelper.saveLanguage(requireContext(),"ENGLISH")
                listener!!.onDataPassed(requireContext().getString(R.string.english_choose))
            }else{
                SharedPreferencesHelper.saveLanguage(requireContext(),"CHINES")
                listener!!.onDataPassed(requireContext().getString(R.string.chines_choose))
            }
            //here i use a timer just to give user expiration he is press on the button
            handler.post(object : Runnable {
                override fun run() {
                    handler.postDelayed(this, 400L)
                    dismiss()
                }
            })

        }
    }

    private fun casting(view: View) {
        radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

