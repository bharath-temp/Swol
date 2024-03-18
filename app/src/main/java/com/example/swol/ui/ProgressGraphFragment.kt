package com.example.swol.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.db.williamchart.view.LineChartView
import com.example.swol.R
import com.example.swol.data.ExerciseEntity
import kotlinx.coroutines.launch
import java.util.Calendar

class ProgressGraphFragment : Fragment() {
    private val viewModel: ExerciseDBViewModel by viewModels()
    private lateinit var lineChart: LineChartView
    private lateinit var timeRangeAutocomplete: AutoCompleteTextView
    private lateinit var exerciseCategoryAutocomplete: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart = view.findViewById(R.id.lineChart)
        timeRangeAutocomplete = view.findViewById(R.id.time_range_autocomplete)
        exerciseCategoryAutocomplete = view.findViewById(R.id.exercise_category_autocomplete)

        val timeRangeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.time_ranges_array,
            android.R.layout.simple_dropdown_item_1line
        )
        timeRangeAutocomplete.setAdapter(timeRangeAdapter)
        timeRangeAutocomplete.setOnItemClickListener { _, _, position, _ ->
            val selectedTimeRange = timeRangeAdapter.getItem(position)
            val (startOfDay, endOfDay) = getTimeRange(selectedTimeRange.toString())
            viewModel.getExercisesForPeriod(startOfDay, endOfDay).observe(viewLifecycleOwner) { exercises ->
                updateChart(exercises)
                updateCategoryDropdown(exercises.map { it.category }.distinct())
            }
        }

        viewModel.getUniqueCategories().observe(viewLifecycleOwner) { categories ->
            updateCategoryDropdown(categories)
        }
    }

    private fun updateChart(exercises: List<ExerciseEntity>) {
        val category = exerciseCategoryAutocomplete.text.toString()
        val chartData = exercises
            .filter { it.category == category }
            .map { Pair(it.date.toString(), (it.weight * it.reps * it.sets)) }
            .sortedBy { it.first }

        Log.d("ProgressGraphFragment", "Chart data: $chartData")
        lineChart.show(chartData)
    }



    private fun getTimeRange(selectedTimeRange: String): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        val endOfDay = calendar.timeInMillis
        when (selectedTimeRange) {
            "Week" -> calendar.add(Calendar.DAY_OF_YEAR, -7)
            "Month" -> calendar.add(Calendar.MONTH, -1)
            "3 Months" -> calendar.add(Calendar.MONTH, -3)
        }
        val startOfDay = calendar.timeInMillis
        return Pair(startOfDay, endOfDay)
    }

    private fun updateCategoryDropdown(categories: List<String>) {
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        exerciseCategoryAutocomplete.setAdapter(categoryAdapter)
    }
}