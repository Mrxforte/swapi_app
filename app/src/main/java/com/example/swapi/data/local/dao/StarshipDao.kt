package com.example.swapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swapi.data.local.entity.StarshipEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StarshipDao {
    @Query("SELECT * FROM starships ORDER BY name ASC")
    fun observeAll(): Flow<List<StarshipEntity>>

    @Query("SELECT * FROM starships WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<StarshipEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<StarshipEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: StarshipEntity)

    @Query("DELETE FROM starships")
    suspend fun clearAll()
}

