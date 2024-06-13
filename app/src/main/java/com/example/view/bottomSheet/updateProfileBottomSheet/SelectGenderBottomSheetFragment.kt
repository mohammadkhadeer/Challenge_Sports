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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.apisetup.R
import com.example.apisetup.databinding.FragmentModalBottomSheetBinding
import com.example.apisetup.notmodel.Status
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

    private var _binding: FragmentModalBottomSheetBinding? = null
    //ui
    private lateinit var radioGroup: RadioGroup
    private lateinit var view_global: View

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

        //fillARadioButton()
        //server
        val activity = activity as? UserProfileActivity
        view_model = SpewViewModel.giveMeViewModelWithHeader(activity!!)
        observeAResponse()

        return view
    }

    private fun observeAResponse() {
        view_model.updateBasicInfo.observe(this){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                Log.i("TAG","myData "+ it.data!!)
                SharedPreferencesHelper.saveProfileInfo(requireContext(), it.data!!.response.data)
                listener!!.onGenderPassed()

                dismiss()

            }else{
                if (it.status == Status.ERROR){
                    //allow to user to try again
//                    error_txt.isVisible = true
//                    error_txt.text = getString(R.string.massage_login_4)
//
//                    press_on_update = true
//                    update_rl.setBackgroundResource(R.drawable.bg_5)
//                    progressBarBlue.isVisible = false

                }

            }
        }
    }


    private fun fillARadioButton() {
        if (selectedgender != null && selectedgender == "male")
        {
            radioGroup.check(R.id.radioButton1)
        }

        if (selectedgender != null && selectedgender == "female")
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
            if (radioButton.text == getString(R.string.male))
            {
                val map = EditProfileTools.makeMapForUpdateNameRequirements("male","gender")
                view_model.updateBasicInfoRequest(map)
            }else{
                val map = EditProfileTools.makeMapForUpdateNameRequirements("female","gender")
                view_model.updateBasicInfoRequest(map)
            }

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

