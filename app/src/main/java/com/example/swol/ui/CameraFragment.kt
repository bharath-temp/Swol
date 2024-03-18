package com.example.swol.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.swol.R

class CameraFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonOpenCamera: Button = view.findViewById(R.id.button_open_camera)
        buttonOpenCamera.setOnClickListener {
            // Implement your logic to open the camera here
        }

        // If you added a RecyclerView or similar component to display images,
        // initialize and set it up here.
    }
}
