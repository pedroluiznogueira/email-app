package com.example.emailapp.repository

import com.example.emailapp.data.EmailDao
import com.example.emailapp.data.EventDao
import com.example.emailapp.model.EmailEntity
import com.example.emailapp.model.EventEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class Repository(private val emailDao: EmailDao, private val eventDao: EventDao) {
    val allEmails: Flow<List<EmailEntity>> = emailDao.getAllEmails()

    fun getEventsByDate(date: LocalDate): Flow<List<EventEntity>> = eventDao.getEventsByDate(date)

    suspend fun insertEmail(email: EmailEntity) {
        emailDao.insertEmail(email)
    }

    suspend fun insertEvent(event: EventEntity) {
        eventDao.insertEvent(event)
    }

    suspend fun deleteEvent(event: EventEntity) {
        eventDao.deleteEvent(event)
    }
}
