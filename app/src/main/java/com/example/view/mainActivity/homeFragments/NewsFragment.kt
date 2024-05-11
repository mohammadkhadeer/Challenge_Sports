package com.example.view.mainActivity.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.challenge.sports.view.fragments.video.VideoBaseFragment
import com.challenge.sports.view.fragments.video.VideosDetailFragment
import com.example.apisetup.R
import com.example.presnter.OnBackPressedListener
import com.example.presnter.OnDetailListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Exception
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "fragType"
private const val ARG_PARAM2 = "param2"


class NewsFragment : Fragment() {
    private var onBackPressedListener: OnBackPressedListener?=null
    // TODO: Rename and change types of parameters
    private var param1: Boolean? = null
    private var param2: String? = null
    private var currentFrag:Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getBoolean(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//       val viewpager= view.findViewById<ViewPager2>(R.id.fragment_viewpager)
//        val fragsList = ArrayList<Fragment>()
//        val frag= NewsInnerFragment.newInstance(",","")
//        val tabLayout=view.findViewById<TabLayout>(R.id.tabs_news)
//        val tabsTitles=ArrayList<String>()
//
//        tabsTitles.add(getString(R.string.news))
//        tabsTitles.add(getString(R.string.highlights))
//
//        frag.setOnDetailListener(object : OnDetailListener {
//            override fun onDetail(propertiesList: List<String>) {
//                showDetailFragment(propertiesList[0])
//            }
//        })
//
//        fragsList.add(frag)
//
//       val vidfrag= VideoBaseFragment.newInstance("","")
//        vidfrag.setOnDetailListener(object : OnDetailListener {
//            override fun onDetail(propertiesList: List<String>) {
//                showDetailFragment(propertiesList[0],propertiesList[1],propertiesList[2].toInt())
//            }
//        })
//
//        fragsList.add(vidfrag)
//        viewpager.adapter= ViewPagerAdapter(requireActivity().supportFragmentManager,requireActivity().lifecycle,fragsList)
//        viewpager.isUserInputEnabled=false
//
//        TabLayoutMediator(tabLayout,viewpager){tab,position->
//            tab.text=tabsTitles[position]
//        }.attach()

    }

    private fun showDetailFragment(postID:String) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragContainer=view?.findViewById<View>(R.id.news_detail_frag_container)
//        val newsDetailFragment=NewsDetailFragment.newInstance(postID,"")

//        transaction.replace(R.id.news_detail_frag_container, newsDetailFragment)
//        transaction.commit()

//        currentFrag=newsDetailFragment
        fragContainer?.visibility=View.VISIBLE
        onBackPressedListener?.changeBackPressBehaviour(this,getString(R.string.news))

    }
    private fun showDetailFragment(videoLink: String, titleDate: String, maxPage: Int) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        val fragContainer = view?.findViewById<View>(R.id.news_detail_frag_container)
        val videosDetailFrag = VideosDetailFragment.newInstance(videoLink, titleDate, maxPage)
        transaction.replace(R.id.news_detail_frag_container, videosDetailFrag)
        transaction.commit()
        currentFrag=videosDetailFrag
        fragContainer?.visibility = View.VISIBLE
        onBackPressedListener?.changeBackPressBehaviour(this,getString(R.string.highlights))
    }
     fun hideDetailFrag(){
        val fragContainer=view?.findViewById<View>(R.id.news_detail_frag_container)
        fragContainer?.visibility=View.GONE
         val transaction: FragmentTransaction =
             requireActivity().supportFragmentManager.beginTransaction()
         try {
             transaction.remove(currentFrag!!).commit()
         }catch (e:Exception){

         }

    }

    fun setChangeBackPressBehaviourListener(onBackPressedListener: OnBackPressedListener ) {
        this.onBackPressedListener=onBackPressedListener
    }

    companion object {

        @JvmStatic
        fun newInstance(isVideo: Boolean, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, isVideo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}