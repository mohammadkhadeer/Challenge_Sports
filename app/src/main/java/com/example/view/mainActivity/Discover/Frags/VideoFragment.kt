package com.example.view.mainActivity.Discover.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.example.apisetup.R
import com.example.model.videos.random.Data

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class VideoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var data:Data? = null
    var videoView : VideoView? = null
    var commentsButton:View ? = null
    var description : TextView? = null
    var profileImg:ImageView? = null
    var userName:TextView?=null
    var upvotesCount:TextView?=null
    var commentsCount:TextView?=null

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
        upvotesCount=view.findViewById(R.id.upvote_count)
        commentsCount=view.findViewById(R.id.comments_count)
        description=view.findViewById(R.id.description_video)
        profileImg=view.findViewById(R.id.profile_img)
        userName=view.findViewById(R.id.follower_name)
        description?.text=data?.description
        userName?.text=data?.triedByUser?.name
        upvotesCount?.text=data?.upvoteCount.toString()
        commentsCount?.text=data?.commentCount.toString()
        Glide.with(requireContext()).load(data?.triedByUser?.profileImg).into(profileImg!!)
        videoView = view.findViewById(R.id.video_view_discover)
        videoView?.setVideoPath(data?.videoUrl) //"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        videoView?.start()
        videoView?.setOnPreparedListener {
            println("Play Now")
            videoView?.start()
        }

        videoView?.setOnCompletionListener { it
            it.start()
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