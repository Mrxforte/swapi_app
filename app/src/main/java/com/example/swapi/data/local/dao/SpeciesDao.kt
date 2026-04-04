package com.example.swapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swapi.data.local.entity.SpeciesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {
    @Query("SELECT * FROM species WHERE id IN (:ids)")
    fun observeByIds(ids: List<String>): Flow<List<SpeciesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SpeciesEntity>)
}
