package com.example.swol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.swol.data.FilteredExercisesRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swol.data.FilteredExercises
import com.example.swol.data.WgerExerciseService
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {
    private val repository = FilteredExercisesRepository(WgerExerciseService.create())
    private val _exerciseApiResults = MutableLiveData<FilteredExercises?>()
    val exerciseApiResults: LiveData<FilteredExercises?> = _exerciseApiResults

    fun loadExerciseSearch(term: String) {
        viewModelScope.launch {
            val result = repository.loadExerciseSearch(term = term)
            _exerciseApiResults.postValue(result.getOrNull())
        }
    }
}