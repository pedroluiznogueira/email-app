package com.example.emailapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.emailapp.data.Converters

@Entity(tableName = "emails")
@TypeConverters(Converters::class)
data class EmailEntity(
    @PrimaryKey val id: Int,
    val subject: String,
    val sender: String,
    val isImportant: Boolean,
    val tags: List<String>
)
