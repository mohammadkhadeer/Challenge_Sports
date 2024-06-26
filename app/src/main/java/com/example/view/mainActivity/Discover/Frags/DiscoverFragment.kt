package com.example.view.mainActivity.Discover.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.apisetup.R
import com.example.apisetup.notmodel.Status
import com.example.model.videos.random.Data
import com.example.viewmodel.SpewViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DiscoverFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mainViewPager:ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = SpewViewModel.giveViewModelVideos(requireActivity())
        viewModel.getRandomVids()
        viewModel.videoRandom.observe(viewLifecycleOwner){ resource->
            when(resource.status){
                Status.SUCCESS -> {
                    mainViewPager?.adapter = VideoViewPagerAdapter(requireActivity(),resource.data!!)
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }
        mainViewPager = view.findViewById(R.id.main_viewpager_discover)


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
class VideoViewPagerAdapter(activity: FragmentActivity,var list: List<Data>): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return  list.size // this indicates number of items or fragments in our case (0,1)
    }

    override fun createFragment(position: Int): Fragment = VideoFragment
        .newInstance("", list[position].videoUrl!!)
        .apply {
        data=list[position]
    }
}
