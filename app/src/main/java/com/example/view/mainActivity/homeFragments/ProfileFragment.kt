package com.challenge.sports.view.HomeActivity.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.example.view.mainActivity.Discover.Frags.StickyHeaderItemDecoration
import com.example.view.mainActivity.Discover.Frags.adapters.ProfileViewAdapter
import com.example.view.mainActivity.Discover.Frags.adapters.containerModel.ProfileFragRvDataClass

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var rvDataClass : List<ProfileFragRvDataClass>? = null
    var mainRecyclerView:RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainRecyclerView=view.findViewById(R.id.main_profile_recycler_view)
        val list:List<String> = listOf("Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok","Cool","Nice","ok")
        rvDataClass = listOf(ProfileFragRvDataClass(ProfileViewAdapter.TYPE_PROFILE,""),
            ProfileFragRvDataClass(ProfileViewAdapter.TYPE_HEADER,"Cool"),
            ProfileFragRvDataClass(ProfileViewAdapter.TYPE_ITEM,list ))
        val layoutManager= GridLayoutManager(requireContext(),3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return when(position){
                    0 -> 3
                    1 -> 3
                    else -> 1
                }
            }
        }
        val adapter=ProfileViewAdapter(rvDataClass!!)
        mainRecyclerView?.layoutManager=layoutManager
        mainRecyclerView?.addItemDecoration(StickyHeaderItemDecoration(adapter,layoutManager))
        mainRecyclerView?.adapter=adapter
        (mainRecyclerView?.adapter as ProfileViewAdapter).notifyDataSetChanged()
        mainRecyclerView?.invalidateItemDecorations()



    }


    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}