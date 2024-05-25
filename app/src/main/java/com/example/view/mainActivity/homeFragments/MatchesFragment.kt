package com.challenge.sports.view.HomeActivity.homeFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.apisetup.notmodel.Resource
import com.example.apisetup.notmodel.Status
import com.example.model.hotMatches.HotMatchBaseClass
import com.example.model.hotMatches.HotMatche
import com.example.model.hotMatches.MatchStatusJ
import com.example.presnter.OnDetailListener
import com.example.presnter.RecyclerViewOnclick
import com.example.presnter.RecyclerViewOnclickMatch
import com.example.utils.GeneralTools.fillMatchesStatus
import com.example.utils.MySharableObject
import com.example.view.mainActivity.homeAdapter.matches.MatchStatusAdapter
import com.example.view.mainActivity.homeAdapter.matches.MatchesAdapter
import com.example.view.matchDetails.MatchDetails
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import java.util.*


class MatchesFragment : Fragment() {
    //ui
    private lateinit var recycler_view: RecyclerView
    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var pro_bar: ProgressBar
    private lateinit var empty_view: RelativeLayout

    //value
    var match_status_list: ArrayList<MatchStatusJ> = ArrayList()
    private var mainAdapter: MatchesAdapter? = null
    private lateinit var vm: MyViewModel

    private var fragmentContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

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

        vm = SpewViewModel.giveMeViewModel(requireActivity())
        vm.getHotMatches()
        //here i will use Int number to detect witch type
        //hot matches number "0" upcoming number "1" ... etc
        handelHotMatchesResponse(vm,0)
    }

    private fun handelHotMatchesResponse(vm: MyViewModel, match_type: Int) {
        when (match_type) {
            0 -> {
                observeTheResponseForHotMatches(vm.matches_root)
            }

            1 -> {
                vm.matches_live.observe(requireActivity()){
                    if (it.status== Status.SUCCESS){
                        passADataToMainAdapter(it.data!!.matchList)
                    }else{
                        Log.i("TAG" ,"data.status "+it.status)
                    }
                }
            }

            2 -> {
                vm.matches_upcoming.observe(requireActivity()){
                    if (it.status== Status.SUCCESS){
                        passADataToMainAdapter(it.data!!.matchList)
                    }else{
                        Log.i("TAG" ,"data.status "+it.status)
                    }
                }
            }
            3 -> {
                vm.matches_finished.observe(requireActivity()){
                    if (it.status== Status.SUCCESS){
                        passADataToMainAdapter(it.data!!.matchList)
                    }else{
                        Log.i("TAG" ,"data.status "+it.status)
                    }
                }
            }
        }

    }

    private fun observeTheResponseForHotMatches(matchesRoot: MutableLiveData<Resource<HotMatchBaseClass>>) {
        matchesRoot.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                passADataToMainAdapter(it.data!!.hotMatches)

            }else{
                //handel error case
                Log.i("TAG" ,"data.status "+it.status)
            }
        }
    }

    private fun passADataToMainAdapter(matches: List<HotMatche?>?) {

        if (matches?.size == 0)
        {
            empty_view.isVisible =true
        }else{
            if (fragmentContext != null){
                mainAdapter = MatchesAdapter(
                    fragmentContext!!,
                    matches!!
                    , object : RecyclerViewOnclickMatch{
                        override fun onClick(position: Int, match_obj: HotMatche) {
                            val intent = Intent(this@MatchesFragment.requireContext(), MatchDetails::class.java)
                            MySharableObject.matchObject=match_obj
                            startActivity(intent)
                        }
                    })

                recyclerViewMain.adapter = mainAdapter
                recyclerViewMain.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                //handel what should be visible what should be gone
                recyclerViewMain.isVisible = true
                empty_view.isVisible = false
                pro_bar.isVisible = false
            }
        }

    }

    private fun createRecyclerViewMatchStatus() {
        recycler_view.setNestedScrollingEnabled(false);
//        recycler_view.adapter= MatchStatusAdapter(requireContext(), match_status_list)
        recycler_view.adapter= MatchStatusAdapter(requireContext(), match_status_list,
            object : RecyclerViewOnclick {
                override fun onClick(position: Int) {

                    handleCaseReq(position)
                    upadateTheSelectedItemInRecyclerView(position)

                    createRecyclerViewMatchStatus()
                }
            })
        recycler_view.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun handleCaseReq(position: Int) {
        recyclerViewMain.isVisible = false
        empty_view.isVisible =false
        pro_bar.isVisible = true


        vm = SpewViewModel.giveMeViewModel(requireActivity())
        when (position) {
            0 -> {
                vm.getHotMatches()
                handelHotMatchesResponse(vm,0)
            }
            1 -> {
                vm.getLiveMatches()
                handelHotMatchesResponse(vm,1)
            }
            2 -> {
                vm.getUpcomingMatches()
                handelHotMatchesResponse(vm,2)
            }
            3 -> {
                vm.getFinishedMatches()
                handelHotMatchesResponse(vm,3)
            }
        }
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
        pro_bar = view.findViewById<ProgressBar>(R.id.pro_bar)
        empty_view = view.findViewById<RelativeLayout>(R.id.empty_view)

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
