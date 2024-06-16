package com.example.emailapp.data

import androidx.room.*
import com.example.emailapp.model.EventEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventDao {
    @Query("SELECT * FROM events WHERE date = :date")
    fun getEventsByDate(date: LocalDate): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)
}