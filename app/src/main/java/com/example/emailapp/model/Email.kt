package com.example.emailapp.model

data class Email(
    val id: Int,
    val subject: String,
    val sender: String,
    var isImportant: Boolean = false,
    val tags: List<String> = listOf()
)