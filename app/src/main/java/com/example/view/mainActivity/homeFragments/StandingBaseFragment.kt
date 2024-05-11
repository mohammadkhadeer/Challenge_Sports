package com.example.view.mainActivity.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.apisetup.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StandingBaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var selectedLeague= PREMIERE_LEAGUE
    var leaguesList= ArrayList<String>()

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
        return inflater.inflate(R.layout.fragment_standing_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val language_rl = view.findViewById<RelativeLayout>(R.id.language_rl)
        val privacy_policy_rl = view.findViewById<RelativeLayout>(R.id.privacy_policy_rl)
        val share_app_rl = view.findViewById<RelativeLayout>(R.id.share_app_rl)
        val feed_back_rl = view.findViewById<RelativeLayout>(R.id.feed_back_rl)
        val rate_us_rl = view.findViewById<RelativeLayout>(R.id.rate_us_rl)
        val exit_app_rl = view.findViewById<RelativeLayout>(R.id.exit_app_rl)

        language_rl.setOnClickListener{
//            (activity as BaseActivity?)?.showDialogForLanguages()
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StandingBaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}