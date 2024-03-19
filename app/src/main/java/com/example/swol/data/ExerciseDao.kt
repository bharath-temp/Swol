package com.example.swol.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: ExerciseEntity)
    @Query("SELECT * FROM ExerciseEntity WHERE date >= :startOfDay AND date < :endOfDay")
    fun getExercisesForDay(startOfDay: Long, endOfDay: Long): Flow<List<ExerciseEntity>>

    @Query("SELECT DISTINCT category FROM ExerciseEntity")
    fun getUniqueCategories(): Flow<List<String>>

    @Delete
    suspend fun delete(exercise: ExerciseEntity)
}