package com.example.swapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swapi.data.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM films ORDER BY episodeId ASC")
    fun observeAll(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<FilmEntity?>

    @Query("SELECT * FROM films WHERE id IN (:ids)")
    fun observeByIds(ids: List<String>): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FilmEntity)

    @Query("DELETE FROM films")
    suspend fun clearAll()
}
