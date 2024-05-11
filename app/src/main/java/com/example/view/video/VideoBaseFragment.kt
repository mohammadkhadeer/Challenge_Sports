package com.challenge.sports.view.fragments.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R

import com.example.presnter.OnBackPressedListener
import com.example.presnter.OnDetailListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoBaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoBaseFragment : Fragment() {
    private var onDetailListener: OnDetailListener?=null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var onBackPressedListener: OnBackPressedListener? = null

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

        return inflater.inflate(R.layout.fragment_video_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val moreVidsRecyclerView = view.findViewById<RecyclerView>(R.id.more_vids_recycler_view)
        val loadingMoreBar=view.findViewById<View>(R.id.loading_more_bar)

//        val viewModel = ViewModelProvider(
//            requireActivity().viewModelStore,
//            ViewModelFactory(ApiHelperImpl(RetroInstance.apiService))
//        ).get(MainViewModel::class.java)


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoBaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun setOnDetailListener(onDetailListener: OnDetailListener) {
        this.onDetailListener=onDetailListener
    }
}