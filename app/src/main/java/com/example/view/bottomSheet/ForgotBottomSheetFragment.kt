package com.example.view.bottomSheet

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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


class ForgotBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentModalBottomSheetBinding? = null
    //ui
    private lateinit var email_edt: EditText
    private lateinit var submit_rl: RelativeLayout
    private lateinit var main_view: RelativeLayout
    private lateinit var loading_rl: RelativeLayout
    private lateinit var main_scc_view: RelativeLayout
    private lateinit var back_to_login_rl: RelativeLayout
    private lateinit var error_txt: TextView
    private lateinit var success_massage_txt: TextView

    //value
    private var emailStr: String = ""
    private lateinit var view_model: MyViewModel
    private var press_on_submit: Boolean = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Apply the custom style here
        return BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgotl_bottom_sheet, container, false)
        casting(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        actionListenerToSubmitButton()
        monitorEmailEdt()

        actionListenerToBackToLogin()

        //create a model from view_model
        view_model = SpewViewModel.giveMeViewModel(requireActivity())
        observeAResponse()
    }

    private fun actionListenerToBackToLogin() {
        back_to_login_rl.setOnClickListener{
            dismiss()
        }
    }

    private fun observeAResponse() {
        view_model.forgot_password.observe(this){
            if (it.status== Status.SUCCESS){
                //handle SUCCESS case
                main_scc_view.isVisible = true
                success_massage_txt.text = it.data!!.response.message[0]

            }else{
                if (it.status == Status.ERROR){
//                    Log.i("TAG" ,"it.message "+it.message)
//                    Log.i("TAG" ,"getString(R.string.massage_login_4) "+getString(R.string.massage_login_4))

                    //allow to user to try again
                    error_txt.isVisible = true
//                    error_txt.text = it.message
                    error_txt.text = getString(R.string.massage_login_4)

                    press_on_submit = true
                    submit_rl.setBackgroundResource(R.drawable.bg_5)
                    loading_rl.isVisible = false
                    //main_view.alpha = 1.0f
                }

            }
        }
    }

    private fun actionListenerToSubmitButton() {
        submit_rl.setOnClickListener {
            if (press_on_submit)
            {
                if (emailStr.isNotEmpty())
                {
                    if (GeneralTools.isValidEmail(emailStr))
                    {
//                        Log.i("TAG","TAG send a email: "+emailStr)
                        //disable submit button
                        press_on_submit = false
                        submit_rl.setBackgroundResource(R.drawable.bg_8)
                        loading_rl.isVisible = true
                        //main_view.alpha = 0.5f

                        //send server request
                        val map = RegisterTools.makeMapForForGotPasswordRequirements(emailStr)
                        view_model.forgotPasswordRequest(map)
                    }else{
                        Toast.makeText(requireContext(), requireContext().getString(R.string.forgot_password_text_3), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), requireContext().getString(R.string.forgot_password_text_2), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun monitorEmailEdt() {
        email_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that the text has been changed
                error_txt.isVisible = false
                emailStr = text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun casting(view: View) {
        email_edt = view.findViewById<EditText>(R.id.email_edt)
        submit_rl = view.findViewById<RelativeLayout>(R.id.submit_rl)
        main_view = view.findViewById<RelativeLayout>(R.id.main_view)
        loading_rl = view.findViewById<RelativeLayout>(R.id.loading_rl)
        main_scc_view = view.findViewById<RelativeLayout>(R.id.main_scc_view)
        error_txt = view.findViewById<TextView>(R.id.textView4)
        success_massage_txt = view.findViewById<TextView>(R.id.success_massage_txt)
        back_to_login_rl = view.findViewById<RelativeLayout>(R.id.back_to_login_rl)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

