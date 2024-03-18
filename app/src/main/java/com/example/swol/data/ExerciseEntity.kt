package com.example.swol.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val weight: Float,
    val sets: Int,
    val reps: Int,
    val date: Long = Calendar.getInstance().timeInMillis
)
