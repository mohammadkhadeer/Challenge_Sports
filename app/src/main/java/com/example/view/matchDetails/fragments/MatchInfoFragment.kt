package com.challenge.sports.view.HomeActivity.homeFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.utils.GeneralTools
import com.example.utils.MySharableObject

class MatchInfoFragment : Fragment() {

    private lateinit var about_match_txt: TextView
    private lateinit var leage_image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.matche_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        casting(view)

        if (MySharableObject.matchObject != null)
        {
            var league_name = GeneralTools.getATextDependALanguage(
                MySharableObject.matchObject?.leagueInfo?.enName.toString()
                , MySharableObject.matchObject?.leagueInfo?.cnName.toString(),requireActivity())

            var home_team = GeneralTools.getATextDependALanguage(
            MySharableObject.matchObject?.homeInfo?.enName.toString()
            , MySharableObject.matchObject?.homeInfo?.cnName.toString(),requireActivity())

            var away_team = GeneralTools.getATextDependALanguage(
            MySharableObject.matchObject?.awayInfo?.enName.toString()
            , MySharableObject.matchObject?.awayInfo?.cnName.toString(),requireActivity())


            var match_time = MySharableObject.matchObject?.matchTiming

            var about_match = context?.getString(R.string.about) +
                    "\n\n"  + home_team +  "  " + context?.getString(R.string.vs) +"  "+ away_team +
                    "\n" + context?.getString(R.string.at) + " " + match_time + "\n\n" + context?.getString(R.string.league_info) +
                    "\n" + context?.getString(R.string.league_name) + " : " + league_name

            about_match_txt.text = about_match

            Glide.with(requireContext()).load(MySharableObject.matchObject?.leagueInfo?.logo).into(leage_image)
        }
    }

    private fun casting(view: View) {
        about_match_txt = view.findViewById(R.id.about_match_txt)
        leage_image     = view.findViewById(R.id.leag_image)
    }
}