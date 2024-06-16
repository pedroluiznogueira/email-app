package com.example.emailapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.emailapp.model.Email

class EmailViewModel : ViewModel() {
    var emails = mutableStateListOf(
        Email(1, "Meeting Schedule", "boss@example.com", true, listOf("Work")),
        Email(2, "Lunch with Friends", "friend@example.com", false, listOf("Personal")),
        Email(3, "Project Update", "colleague@example.com", false, listOf("Work")),
        Email(4, "Invitation to Event", "event@invite.com", false, listOf("Events")),
        Email(5, "Newsletter", "newsletter@example.com", false, listOf("Promotions")),
    )
}