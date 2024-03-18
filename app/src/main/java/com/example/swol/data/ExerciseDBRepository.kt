package com.example.swol.data

import kotlinx.coroutines.flow.Flow

class ExerciseDBRepository(
    private val dao: ExerciseDao
) {
    suspend fun insertExercise(exercise: ExerciseEntity) = dao.insert(exercise)

    fun getExercisesForDay(startOfDay: Long, endOfDay: Long): Flow<List<ExerciseEntity>> {
        return dao.getExercisesForDay(startOfDay, endOfDay)
    }

    fun getUniqueCategories(): Flow<List<String>> {
        return dao.getUniqueCategories()
    }
}