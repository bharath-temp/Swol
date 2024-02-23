package com.example.swol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.data.FilteredExercisesRepository

class MainActivity : AppCompatActivity() {
    private val viewModel: ExerciseViewModel by viewModels()
    private lateinit var exercisesAdapter: ExercisesAdapter
    private lateinit var exerciseListRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBoxET: EditText = findViewById(R.id.et_search_box)
        val searchBtn: Button = findViewById(R.id.btn_search)

        exerciseListRV = findViewById<RecyclerView>(R.id.rv_search_results)

        exercisesAdapter = ExercisesAdapter(listOf())
        exerciseListRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = exercisesAdapter
        }

        // Observe LiveData from ViewModel and update adapter
        viewModel.exerciseApiResults.observe(this) { filteredExercises ->
            filteredExercises?.let {
                val datapoints = it.exercises.map { exercise -> exercise.data }
                exercisesAdapter.updateExercises(datapoints)
            }
        }

        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            if (!TextUtils.isEmpty(query)) {
                viewModel.loadExerciseSearch(query)
                exerciseListRV.scrollToPosition(0)
            }
        }

    }
}