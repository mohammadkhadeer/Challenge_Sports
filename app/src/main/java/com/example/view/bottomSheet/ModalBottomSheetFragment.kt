package com.example.view.bottomSheet

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.databinding.FragmentModalBottomSheetBinding
import com.example.model.odds.OddsCompanyComp
import com.example.presnter.FillCompanyInfo
import com.example.presnter.RecyclerViewOnclickCompany
import com.example.utils.GeneralTools
import com.example.utils.SelectedCompanyObj
import com.example.view.matchDetails.Adapters.AdapterCompanyList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class ModalBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentModalBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycler_view: RecyclerView
    private var company_list: ArrayList<OddsCompanyComp> = ArrayList()
    var adpterCompanyList: AdapterCompanyList? = null
    private val companyObjShared: SelectedCompanyObj? = null

    private var listener: FillCompanyInfo? = null

    fun setListener(listener: FillCompanyInfo) {
        this.listener = listener
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = activity as FillCompanyInfo
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement MyInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modal_bottom_sheet, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)
        //fill odds company
        company_list = GeneralTools.fillCompanyList(requireActivity())


        createACompanyList(view)
    }

    private fun createACompanyList(view: View) {
        adpterCompanyList = AdapterCompanyList(
            requireContext(),
            company_list!!
            , object : RecyclerViewOnclickCompany {
                override fun onClick(position: Int, company_obj: OddsCompanyComp) {
                    Log.i("TAG","TAG company_obj: "+company_obj.name)
                    companyObjShared?.selectValue(company_obj)
                    listener?.onFill(company_obj)
                    dismiss()
                }
            })

        recycler_view.adapter = adpterCompanyList
        recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun casting(view: View) {
        recycler_view = view.findViewById<RecyclerView>(R.id.company_recycler_view)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

