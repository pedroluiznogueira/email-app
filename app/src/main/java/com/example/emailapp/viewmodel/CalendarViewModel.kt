package com.example.emailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailapp.model.EventEntity
import com.example.emailapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(private val repository: Repository) : ViewModel() {
    private val _events = MutableStateFlow<Map<LocalDate, List<EventEntity>>>(emptyMap())
    val events = _events.asStateFlow()

    fun getEventsForDate(date: LocalDate) {
        viewModelScope.launch {
            repository.getEventsByDate(date).collect { eventList ->
                _events.value = _events.value.toMutableMap().apply { put(date, eventList) }
            }
        }
    }

    fun addEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.insertEvent(event)
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.deleteEvent(event)
            // Refresh the events for the currently selected date
            getEventsForDate(event.date)
        }
    }
}