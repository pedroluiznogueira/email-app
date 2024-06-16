package com.example.emailapp.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class CalendarViewModel : ViewModel() {
    val events = mutableStateMapOf<LocalDate, MutableList<String>>()
}