package com.example.emailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailapp.model.EmailEntity
import com.example.emailapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailViewModel(private val repository: Repository) : ViewModel() {
    private val _emails = MutableStateFlow<List<EmailEntity>>(emptyList())
    val emails = _emails.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allEmails.collect {
                _emails.value = it
            }
        }
    }

    fun addEmail(email: EmailEntity) {
        viewModelScope.launch {
            repository.insertEmail(email)
        }
    }
}
