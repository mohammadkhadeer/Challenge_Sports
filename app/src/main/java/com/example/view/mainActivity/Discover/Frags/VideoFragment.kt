package com.example.view.mainActivity.Discover.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import com.example.apisetup.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class VideoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    var videoView : VideoView? = null
    var commentsButton:View ? = null
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
        return inflater.inflate(R.layout.fragment_video_resource, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        videoView = view.findViewById(R.id.video_view_discover)

        videoView?.setVideoPath("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        videoView?.start()
        videoView?.setOnPreparedListener {
            println("Play Now")
            videoView?.start()
        }
        commentsButton = view.findViewById(R.id.comments_btn)
        commentsButton?.setOnClickListener {
            val commentsBottomSheetFragment = CommentBottomSheetFrag()
            commentsBottomSheetFragment.show(childFragmentManager, "commentsBottomSheet")

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}