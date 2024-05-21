package com.challenge.sports.view.HomeActivity.homeFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.odds.OddsCompanyComp
import com.example.utils.GeneralTools
import com.example.utils.MySharableObject
import com.example.utils.SelectedCompanyObj
import com.example.view.bottomSheet.ModalBottomSheetFragment
import com.example.view.matchDetails.Adapters.AdapterOdds
import com.example.view.matchDetails.Adapters.AdapterOddsHandicap
import com.example.viewmodel.MyViewModel
import com.example.viewmodel.SpewViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class OddsFragment : Fragment()  {
    private lateinit var tabLayout: TabLayout
    private lateinit var vm: MyViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var recycler_view_handicap: RecyclerView
    private lateinit var empty_view_rl: RelativeLayout
    private lateinit var pro_bar: ProgressBar
    private lateinit var company_image: ImageView
    private lateinit var company_image_handicap: ImageView

    private lateinit var teams_info_ll: LinearLayout
    private lateinit var teams_info_ll_handicap: LinearLayout

    private var oddsAdapter: AdapterOdds? = null
    private var oddsAdapterHandicap: AdapterOddsHandicap? = null

    private lateinit var header_text_1: TextView
    private lateinit var header_text_2: TextView
    private lateinit var header_text_3: TextView


    //use it to pass a value from bottomSheet to this fragment
    //where create a object and set observer to see when this object will get a data
    private val sharedViewModel: SelectedCompanyObj by activityViewModels()
    //i use this to can pass a company id from tabLayout "to win,handicap ..etc"
    //i get a value from observer i will put it on this vale to can access from
    //tabLayout the default value for a company_id is "2"
    //the bet365
    private var company_selected: OddsCompanyComp? = null
    private var selected_tab: String = "eu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matche_odds_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        //Create a default object and fill a company image
        fillBasicInfo()

        actionListenerToCompanyImage()

        addATabsToTabLayout()

        tabLayoutController()

        vm = SpewViewModel.giveMeViewModel(requireActivity())

        if (MySharableObject.matchObject != null)
        {
            Log.i("TAG","TAG MySharableObject.matchObject?.id!! "+MySharableObject.matchObject?.id!!)
            vm.getMatchOdds(MySharableObject.matchObject?.id!!)
        }

        //odds type To win in response is asia
        createOddList(selected_tab!!,company_selected!!.id)

        //when the select to see all a company avalible to see the bet
        //i active this observer to can pass a selected value from a bottom sheet to the odds fragment
        sharedViewModel.selectedValue.observe(viewLifecycleOwner, Observer { value ->
            Log.i("TAG","TAG OddsFragment company_obj: "+value.name)
            company_selected = value
            updateImage()
            if (selected_tab == "")
            {
                passADataToOddsAdapterHandicap(company_selected!!.id)
            }else{
                createOddList(selected_tab,company_selected!!.id)
            }
        })
    }

    private fun actionListenerToCompanyImage() {
        company_image.setOnClickListener {
            val modalBottomSheet = ModalBottomSheetFragment()
            modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheetFragment::class.java.simpleName)
        }

        company_image_handicap.setOnClickListener {
            val modalBottomSheet = ModalBottomSheetFragment()
            modalBottomSheet.show(parentFragmentManager, ModalBottomSheetFragment::class.java.simpleName)
        }
    }

    private fun fillBasicInfo() {
        company_selected = OddsCompanyComp(requireActivity().getString(R.string.BET365), R.drawable.bet365,2)
        //image for all cases
        updateImage()
    }

    private fun updateImage() {
        Glide.with(requireActivity()).load(company_selected!!.image_path).into(company_image)
        Glide.with(requireActivity()).load(company_selected!!.image_path).into(company_image_handicap)
    }

    private fun addATabsToTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.to_win)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.handicap)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.goals)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.corners)))
    }

    private fun tabLayoutController() {

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0->{
                        handleToWinHeader()

                        //odds type To win in response is asia
                        selected_tab = "eu"
                        createOddList(selected_tab,company_selected!!.id)
                    }
                    1->{
                        handleHandicapHeader()

                        //odds type handicap in response is asia
                        //createOddList("eu")
                        selected_tab = "asia"
                        passADataToOddsAdapterHandicap(company_selected!!.id)
                    }
                    2->{
                        handleGoalsHeader()

                        //odds type goals in response is asia
                        selected_tab = "bs"
                        createOddList(selected_tab,company_selected!!.id)
                    }
                    3->{
                        handleCornersHeader()

                        //odds type corners in response is asia
                        selected_tab = "cr"
                        createOddList(selected_tab,company_selected!!.id)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.getTabAt(0)?.select();
    }

    private fun handleCornersHeader() {
        header_text_1.text = requireContext().getString(R.string.corners)
        header_text_2.text = requireContext().getString(R.string.over)
        header_text_3.text = requireContext().getString(R.string.under)

        recycler_view.isVisible = true
        recycler_view_handicap.isVisible = false

        teams_info_ll_handicap.isVisible = false
        teams_info_ll.isVisible = true
    }

    private fun handleGoalsHeader() {
        header_text_1.text = requireContext().getString(R.string.goal)
        header_text_2.text = requireContext().getString(R.string.over)
        header_text_3.text = requireContext().getString(R.string.under)


        recycler_view.isVisible = true
        recycler_view_handicap.isVisible = false

        teams_info_ll_handicap.isVisible = false
        teams_info_ll.isVisible = true
    }

    private fun handleHandicapHeader() {
        recycler_view.isVisible = false
        recycler_view_handicap.isVisible = true

        teams_info_ll_handicap.isVisible = true
        teams_info_ll.isVisible = false
    }

    private fun handleToWinHeader() {
        header_text_1.text = requireContext().getString(R.string.home_team)
        header_text_2.text = requireContext().getString(R.string.x)
        header_text_3.text = requireContext().getString(R.string.away)

        recycler_view.isVisible = true
        recycler_view_handicap.isVisible = false

        teams_info_ll_handicap.isVisible = false
        teams_info_ll.isVisible = true
    }

    private fun casting(view: View) {
        //odds_recycler_view_handicap
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutOddsFragments)
        recycler_view = view.findViewById<RecyclerView>(R.id.odds_recycler_view)
        recycler_view_handicap = view.findViewById<RecyclerView>(R.id.odds_recycler_view_handicap)

        teams_info_ll = view.findViewById<LinearLayout>(R.id.teams_info_ll)
        teams_info_ll_handicap = view.findViewById<LinearLayout>(R.id.teams_info_ll_handicap)

        header_text_1 = view.findViewById<TextView>(R.id.header_text_1)
        header_text_2 = view.findViewById<TextView>(R.id.header_text_2)
        header_text_3 = view.findViewById<TextView>(R.id.header_text_3)
        company_image = view.findViewById<ImageView>(R.id.company_image)
        company_image_handicap = view.findViewById<ImageView>(R.id.company_image_handicap)

        empty_view_rl = view.findViewById<RelativeLayout>(R.id.empty_view_rl)
        pro_bar = view.findViewById<ProgressBar>(R.id.pro_bar)
    }


    private fun createOddList(odds_type:String,company_id:Int) {
        vm.matches_odds.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                pro_bar.isVisible = false

                var odd_list_data = GeneralTools.filterOddRoot(company_id,it.data!!,odds_type)

                if (odd_list_data.size == 0) {
                    empty_view_rl.isVisible = true
                    recycler_view.isVisible = false
                }else{
                    empty_view_rl.isVisible = false
                    recycler_view.isVisible = true

                    oddsAdapter = AdapterOdds(requireContext(),odd_list_data)
                    recycler_view.adapter = oddsAdapter
                    recycler_view.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

            }else{
                Log.i("TAG" ,"data.status "+it.status)
            }
        }
    }

    private fun passADataToOddsAdapterHandicap(company_id:Int) {
        vm.matches_odds.observe(requireActivity()){
            if (it.status== Status.SUCCESS){
                var odd_list_data = GeneralTools.filterOddRoot(company_id,it.data!!,"eu")

                if (odd_list_data.size == 0) {
                    empty_view_rl.isVisible = true
                    recycler_view.isVisible = false
                }else{
                    oddsAdapterHandicap = AdapterOddsHandicap(requireContext(), odd_list_data)

                    recycler_view_handicap.adapter = oddsAdapterHandicap
                    recycler_view_handicap.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

            }else{
                Log.i("TAG" ,"data.status "+it.status)
            }
        }

    }

}

