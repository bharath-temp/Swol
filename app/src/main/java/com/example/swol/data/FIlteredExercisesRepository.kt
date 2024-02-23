package com.example.swol.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilteredExercisesRepository(
    private val service: WgerExerciseService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadExerciseSearch(term: String): Result<FilteredExercises?> = withContext(ioDispatcher){
        try {
            val response = service.searchExercise(term = term)
            if(response.isSuccessful){
                Log.d("CoRo", "Network Call")
                Result.success(response.body())
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}