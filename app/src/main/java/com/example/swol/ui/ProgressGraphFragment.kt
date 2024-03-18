package com.example.swol.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.db.williamchart.view.BarChartView
import com.db.williamchart.view.LineChartView
import com.example.swol.R
import com.example.swol.databinding.ActivityMainBinding


class ProgressGraphFragment : Fragment() {
    private lateinit var barChartVer: BarChartView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_graph, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barChartVer = view.findViewById(R.id.barChartVer)

        val mySet = linkedMapOf("label1" to 4F, "label2" to 7F, "label3" to 2F)
        barChartVer.show(mySet)
    }
}
