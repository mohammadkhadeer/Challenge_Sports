package com.challenge.sports.view.HomeActivity.homeFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.hotMatches.MatchStatusJ
import com.example.presnter.OnDetailListener
import com.example.presnter.RecyclerViewOnclick
import com.example.utils.GeneralTools.fillMatchesStatus
import com.example.view.mainActivity.homeAdapter.matches.MatchStatusAdapter
import com.example.view.mainActivity.homeAdapter.matches.MatchesAdapter
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.example.viewmodel.ViewModelFactory
import java.util.*


class MatchesFragment : Fragment() {

    private lateinit var recycler_view: RecyclerView
    private lateinit var recyclerViewMain: RecyclerView

    var match_status_list: ArrayList<MatchStatusJ> = ArrayList()
    var adpterMatchStatus: MatchStatusAdapter? = null
    private var mainAdapter: MatchesAdapter? = null
    private var onDetailListener: OnDetailListener?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matches_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)
        match_status_list = fillMatchesStatus(requireContext())
        createRecyclerViewMatchStatus()

        val vm= SpewViewModel.giveMeViewModel(requireActivity())
        vm.makeCallAPI()
        handelHotMatchesResponse(vm)
    }

    private fun handelHotMatchesResponse(vm: MyViewModel) {
        vm.matches_root.observe(requireActivity()){
            if (it.status== Status.SUCCESS){

                if (it.data!!.hotMatches?.get(0) != null)
                {
                    passADataToMainAdapter(it)
                }

            }else{
                //handel error case
                Log.i("TAG" ,"data.status "+it.status)
            }
        }

    }


    private fun passADataToMainAdapter(it: Resource<HotMatchBaseClass>?) {
        mainAdapter = MatchesAdapter(
            requireContext(),
            it!!.data!!
        )

        recyclerViewMain.adapter = mainAdapter
        recyclerViewMain.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun createRecyclerViewMatchStatus() {
        recycler_view.setNestedScrollingEnabled(false);
//        recycler_view.adapter= MatchStatusAdapter(requireContext(), match_status_list)
        recycler_view.adapter= MatchStatusAdapter(requireContext(), match_status_list,
            object : RecyclerViewOnclick {
                override fun onClick(position: Int) {
                    upadateTheSelectedItemInRecyclerView(position)

                    createRecyclerViewMatchStatus()
                }
            })
        recycler_view.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun upadateTheSelectedItemInRecyclerView(position: Int) {
        for (i in 0..(match_status_list.size - 1)) {
            if (position == i)
            {
                match_status_list.set(position,MatchStatusJ(match_status_list.get(position).name, true))
            }else{
                match_status_list.set(i,MatchStatusJ(match_status_list.get(i).name, false))
            }

        }
    }

    private fun casting(view: View) {
        recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerViewMain = view.findViewById<RecyclerView>(R.id.main_recycler_view)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MatchesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}
