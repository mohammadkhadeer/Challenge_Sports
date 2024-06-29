package com.example.view.bottomSheet.updateProfileBottomSheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.example.apisetup.R
import com.example.apisetup.databinding.FragmentModalBottomSheetBinding
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.editProfile.serverModel.UserUpdateInfo
import com.example.presnter.GenderSheetListener
import com.example.presnter.LanguageBottomSheetListener
import com.example.sharedPreferences.SharedPreferencesHelper
import com.example.utils.EditProfileTools
import com.example.view.userProfileActivity.UserProfileActivity
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SelectGenderBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "MyBottomSheetFragment"
    }

    private var _binding: FragmentModalBottomSheetBinding? = null
    //ui
    private lateinit var radioGroup: RadioGroup
    private lateinit var view_global: View
    private lateinit var progress_bar: ProgressBar
    private lateinit var relativeLayout: RelativeLayout

    //value
    private var selectedgender: String = ""
    private val handler = Handler(Looper.getMainLooper())

    //interface
    var listener: GenderSheetListener? = null

    //server
    private lateinit var view_model: MyViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        selectedgender = SharedPreferencesHelper.getGender(requireContext()).toString()
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Apply the custom style here
        return BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.gender_select_bottom_sheet, container, false)
        casting(view)
        view_global = view

        fillARadioButton()

        //server
        val activity = activity as? UserProfileActivity
        view_model = SpewViewModel.giveMeViewModelWithHeader(activity!!)

        return view
    }

    private fun fillARadioButton() {
        if (selectedgender != null && selectedgender == "Male")
            radioGroup.check(R.id.radioButton1)

        if (selectedgender != null && selectedgender == "Female")
            radioGroup.check(R.id.radioButton2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionListenerToRadioButton()
    }

    private fun actionListenerToRadioButton() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            progress_bar.isVisible = true
            relativeLayout.isVisible = true

            val radioButton: RadioButton = view_global.findViewById(checkedId)
            if (radioButton.text == getString(R.string.male))
            {
                listener!!.onGenderPassed("male")
            }else{
                listener!!.onGenderPassed("female")
            }

            dismiss()
        }
    }

    private fun casting(view: View) {
        radioGroup     = view.findViewById<RadioGroup>(R.id.radioGroup)
        progress_bar   = view.findViewById<ProgressBar>(R.id.progressBar)
        relativeLayout = view.findViewById<RelativeLayout>(R.id.relativeLayout)

        progress_bar.isVisible = false
        relativeLayout.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismiss()
        _binding = null
    }
}

