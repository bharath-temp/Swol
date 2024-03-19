package com.example.swol.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.db.williamchart.view.LineChartView
import com.example.swol.R
import com.example.swol.data.ExerciseEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
             updateChartData()
        }

        exerciseCategoryAutocomplete.setOnItemClickListener { _, _, position, _ ->
            updateChartData()
        }

        viewModel.getUniqueCategories().observe(viewLifecycleOwner) { categories ->
            updateCategoryDropdown(categories)
        }
    }

    private fun updateChart(exercises: List<ExerciseEntity>, selectedTimeRange: String) {
        val category = exerciseCategoryAutocomplete.text.toString()
        val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault()) // Define a date format

        // Prepare the chart data as a List of Pairs
        val chartData = exercises
            .filter { it.category == category }
            .sortedBy { it.date }
            .map {
                // Format the date as a string to use as the label
                val label = dateFormat.format(it.date)
                // Calculate the total weight and use it as the value
                val value = (it.weight * it.reps * it.sets).toFloat()

                // Return each item as a Pair
                Pair(label, value)
            }

        Log.d("ProgressGraphFragment", "Chart data: $chartData")
        lineChart.show(chartData)

        // Define the gradient
        val gradientColors = intArrayOf(
            ContextCompat.getColor(requireContext(), R.color.gradient_start_color),
            ContextCompat.getColor(requireContext(), R.color.gradient_end_color)
        )

        // Apply the gradient to the chart
        lineChart.gradientFillColors = gradientColors
    }

    private fun updateChartData() {
        val selectedTimeRange = timeRangeAutocomplete.text.toString()
        val (startOfDay, endOfDay) = getTimeRange(selectedTimeRange)
        viewModel.getExercisesForPeriod(startOfDay, endOfDay).observe(viewLifecycleOwner) { exercises ->
            updateChart(exercises, selectedTimeRange)
        }
    }


    private fun getTimeRange(selectedTimeRange: String): Pair<Long, Long> {
        val endCalendar = Calendar.getInstance()

        // Set time to end of the day for accurate 'endOfDay'
        endCalendar.set(Calendar.HOUR_OF_DAY, 23)
        endCalendar.set(Calendar.MINUTE, 59)
        endCalendar.set(Calendar.SECOND, 59)
        endCalendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = endCalendar.timeInMillis

        val startCalendar = Calendar.getInstance()
        when (selectedTimeRange) {
            "Week" -> startCalendar.add(Calendar.WEEK_OF_YEAR, -1)
            "Month" -> startCalendar.add(Calendar.MONTH, -1)
            "3 Months" -> startCalendar.add(Calendar.MONTH, -3)
        }

        // Set time to start of the day for accurate 'startOfDay'
        startCalendar.set(Calendar.HOUR_OF_DAY, 0)
        startCalendar.set(Calendar.MINUTE, 0)
        startCalendar.set(Calendar.SECOND, 0)
        startCalendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = startCalendar.timeInMillis

        return Pair(startOfDay, endOfDay)
    }

    private fun updateCategoryDropdown(categories: List<String>) {
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        exerciseCategoryAutocomplete.setAdapter(categoryAdapter)
    }
}