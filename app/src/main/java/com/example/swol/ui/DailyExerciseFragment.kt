package com.example.swol.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DailyExerciseFragment : Fragment(R.layout.fragment_daily_exercise_summary) {
    private lateinit var exerciseAdapter: DailyExerciseAdapter
    private val dbViewModel: ExerciseDBViewModel by viewModels()
    private var selectedDate: Date = Calendar.getInstance().time
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectDateButton: Button = view.findViewById(R.id.btn_select_date)
        val nextDayButton: ImageButton = view.findViewById(R.id.id_forward)
        val prevDayButton: ImageButton = view.findViewById(R.id.id_backward)

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        selectDateButton.text = dateFormat.format(currentDate)

        val exerciseRecyclerView: RecyclerView = view.findViewById(R.id.exercise_recycler_view)
        exerciseAdapter = DailyExerciseAdapter(listOf())
        exerciseRecyclerView.adapter = exerciseAdapter
        exerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        updateExerciseData(currentDate)

        selectDateButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(Calendar.getInstance().timeInMillis)
                .build()
            datePicker.show(childFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection
                val selectedDate = calendar.time
                selectDateButton.text = dateFormat.format(selectedDate)

                updateExerciseData(selectedDate)
                Log.d("Date:", "Selected date: ${dateFormat.format(selectedDate)}")
            }
        }

        nextDayButton.setOnClickListener {
            changeDate(1)
        }

        prevDayButton.setOnClickListener {
            changeDate(-1)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.swol_exercise_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_share -> {
                            share(selectedDate)
                            true
                        }
                        R.id.action_settings -> {
                            findNavController().navigate(R.id.navigate_to_settings)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
    }

    private fun getPreferredUnits(): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        return sharedPreferences.getString("units", "lbs") ?: "lbs"
    }

    private fun share(selectedDate: Date) {
        val shareText = getExerciseDetailsForSharing(selectedDate)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }

    private fun getExerciseDetailsForSharing(date: Date): String {
        val exercises = exerciseAdapter.getExercises()
        if (exercises.isEmpty()) {
            return "No exercises recorded on ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)}"
        }

        val stringBuilder = StringBuilder()
        stringBuilder.append("Exercises for ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)}:\n\n")

        exercises.forEach { exercise ->
            stringBuilder.append("${exercise.name} - ${exercise.weight} ${getPreferredUnits()}, ${exercise.sets} sets, ${exercise.reps} reps\n")
        }

        return stringBuilder.toString().trim()
    }

    private fun updateExerciseData(date: Date) {
        val startOfDay = getStartOfDay(date.time)
        val endOfDay = getEndOfDay(date.time)

        dbViewModel.getExercisesForPeriod(startOfDay, endOfDay).observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.submitList(exercises)
        }
    }

    private fun getStartOfDay(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfDay(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun changeDate(days: Int) {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        calendar.add(Calendar.DAY_OF_MONTH, days)
        selectedDate = calendar.time

        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        view?.findViewById<Button>(R.id.btn_select_date)?.text = dateFormat.format(selectedDate)

        updateExerciseData(selectedDate)
    }

}
