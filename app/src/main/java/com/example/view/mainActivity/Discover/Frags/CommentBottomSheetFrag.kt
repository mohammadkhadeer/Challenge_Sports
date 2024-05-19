package com.example.view.mainActivity.Discover.Frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.apisetup.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CommentBottomSheetFrag : BottomSheetDialogFragment() {
    private lateinit var recyclerViewComments: RecyclerView
    private lateinit var editTextComment: EditText
    private lateinit var buttonSendComment: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_comments, container, false)

        recyclerViewComments = view.findViewById(R.id.recyclerViewComments)
        editTextComment = view.findViewById(R.id.editTextComment)
        buttonSendComment = view.findViewById(R.id.buttonSendComment)
        buttonSendComment.setOnClickListener {

        }

        return view
    }




}
