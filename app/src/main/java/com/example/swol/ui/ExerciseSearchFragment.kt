package com.example.swol.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.R

class ExerciseSearchFragment : Fragment(R.layout.fragment_exercise_search) {
    private val viewModel: ExerciseSearchViewModel by viewModels()
    private lateinit var exerciseSearchAdapter: ExerciseSearchAdapter
    private lateinit var exerciseListRV: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchBoxET: EditText = view.findViewById(R.id.et_search_box)
        val searchBtn: Button = view.findViewById(R.id.btn_search)
        val weightET: EditText = view.findViewById(R.id.et_weight)
        val setsET: EditText = view.findViewById(R.id.et_sets)
        val repsET: EditText = view.findViewById(R.id.et_reps)
        val saveBtn: Button = view.findViewById(R.id.btn_save)

        exerciseListRV = view.findViewById<RecyclerView>(R.id.rv_search_results)

        exerciseSearchAdapter = ExerciseSearchAdapter(listOf())
        exerciseListRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseSearchAdapter
        }

        // Observe LiveData from ViewModel and update adapter
        viewModel.exerciseApiResults.observe(viewLifecycleOwner) { filteredExercises ->
            filteredExercises?.let {
                val datapoints = it.exercises.map { exercise -> exercise.data }
                exerciseSearchAdapter.updateExercises(datapoints)
            }
        }

        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            if (!TextUtils.isEmpty(query)) {
                viewModel.loadExerciseSearch(query)
                exerciseListRV.scrollToPosition(0)
            }
        }

        saveBtn.setOnClickListener {
            val selectedExercise = exerciseSearchAdapter.getSelectedItem()
            val weight = weightET.text.toString()
            val sets = setsET.text.toString()
            val reps = repsET.text.toString()

            if (selectedExercise != null) {
                Log.d("ExerciseLog", "Exercise: ${selectedExercise.name}, Category: ${selectedExercise.category}, Weight: $weight, Sets: $sets, Reps: $reps")
            } else {
                Log.d("ExerciseLog", "No exercise selected")
            }
        }
    }
}