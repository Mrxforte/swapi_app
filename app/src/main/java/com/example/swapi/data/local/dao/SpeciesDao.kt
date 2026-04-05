package com.example.swapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swapi.data.local.entity.SpeciesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {
    @Query("SELECT * FROM species ORDER BY name ASC")
    fun observeAll(): Flow<List<SpeciesEntity>>

    @Query("SELECT * FROM species WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<SpeciesEntity?>

    @Query("SELECT * FROM species WHERE id IN (:ids)")
    fun observeByIds(ids: List<String>): Flow<List<SpeciesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SpeciesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SpeciesEntity)

    @Query("DELETE FROM species")
    suspend fun clearAll()
}
