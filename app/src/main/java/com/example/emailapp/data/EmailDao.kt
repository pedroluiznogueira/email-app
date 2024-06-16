package com.example.emailapp.data

import androidx.room.*
import com.example.emailapp.model.EmailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmailDao {
    @Query("SELECT * FROM emails")
    fun getAllEmails(): Flow<List<EmailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmail(email: EmailEntity)

    @Delete
    suspend fun deleteEmail(email: EmailEntity)
}