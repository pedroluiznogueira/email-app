package com.example.emailapp.view.screens.emaillist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterDropdown(selectedTag: String, onTagSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val tags = listOf("All", "Work", "Personal", "Events", "Promotions")

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { expanded = !expanded }
    ) {
        Text(text = selectedTag, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            tags.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(tag) },
                    onClick = {
                        onTagSelected(tag)
                        expanded = false
                    }
                )
            }
        }
    }
}
