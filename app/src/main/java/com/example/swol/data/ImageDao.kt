package com.example.swol.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert
    suspend fun insertImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ImageEntity ORDER BY id DESC")
    fun getAllImages(): Flow<List<ImageEntity>>
}
