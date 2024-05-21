package com.example.view.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.databinding.FragmentModalBottomSheetBinding
import com.example.model.odds.OddsCompanyComp
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
    private val sharedViewModel: SelectedCompanyObj by activityViewModels()


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
                    sharedViewModel.selectValue(company_obj)
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

