package com.example.swapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swapi.data.local.entity.PlanetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets ORDER BY name ASC")
    fun observeAll(): Flow<List<PlanetEntity>>

    @Query("SELECT * FROM planets WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<PlanetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PlanetEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PlanetEntity)

    @Query("DELETE FROM planets")
    suspend fun clearAll()
}

