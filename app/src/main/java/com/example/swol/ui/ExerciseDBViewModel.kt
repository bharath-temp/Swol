package com.example.swol.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.swol.data.AppDatabase
import com.example.swol.data.ExerciseDBRepository
import com.example.swol.data.ExerciseEntity
import kotlinx.coroutines.launch

class ExerciseDBViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ExerciseDBRepository(
        AppDatabase.getInstance(application).exerciseDao()
    )

    fun addExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repository.insertExercise(exercise)
        }
    }

    fun deleteExercise(exercise: ExerciseEntity){
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }

    fun getExercisesForPeriod(startOfDay: Long, endOfDay: Long) = repository.getExercisesForDay(startOfDay, endOfDay).asLiveData()

    fun getUniqueCategories() = repository.getUniqueCategories().asLiveData()
}