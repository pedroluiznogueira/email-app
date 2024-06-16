package com.example.emailapp.view.screens.emaillist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.emailapp.model.EmailEntity
import com.example.emailapp.viewmodel.EmailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailListScreen(navController: NavHostController, viewModel: EmailViewModel) {
    val emails by viewModel.emails.collectAsState(initial = emptyList())

    var searchQuery by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("All") }
    var showDialog by remember { mutableStateOf(false) }
    var newEmailSubject by remember { mutableStateOf("") }
    var newEmailSender by remember { mutableStateOf("") }
    var newEmailTags by remember { mutableStateOf("") }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SearchBar(searchQuery) { newQuery ->
                    searchQuery = newQuery
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            FilterDropdown(selectedTag) { newTag ->
                selectedTag = newTag
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.navigate("calendar") }) {
                Text("Go to Calendar")
            }
            Button(onClick = { showDialog = true }) {
                Text("Add Email")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filteredEmails = emails.filter {
                (it.subject.contains(searchQuery, ignoreCase = true) ||
                        it.sender.contains(searchQuery, ignoreCase = true)) &&
                        (selectedTag == "All" || it.tags.contains(selectedTag))
            }
            items(filteredEmails) { email ->
                EmailItem(
                    email = email,
                    onImportantToggle = {
                        viewModel.addEmail(email.copy(isImportant = !email.isImportant))
                    }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add New Email") },
            text = {
                Column {
                    TextField(
                        value = newEmailSubject,
                        onValueChange = { newEmailSubject = it },
                        label = { Text("Subject") }
                    )
                    TextField(
                        value = newEmailSender,
                        onValueChange = { newEmailSender = it },
                        label = { Text("Sender") }
                    )
                    TextField(
                        value = newEmailTags,
                        onValueChange = { newEmailTags = it },
                        label = { Text("Tags (comma separated)") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val tags = newEmailTags.split(",").map { it.trim() }
                    val newEmail = EmailEntity(
                        id = (emails.maxOfOrNull { it.id } ?: 0) + 1,
                        subject = newEmailSubject,
                        sender = newEmailSender,
                        isImportant = false,
                        tags = tags
                    )
                    viewModel.addEmail(newEmail)
                    showDialog = false
                    newEmailSubject = ""
                    newEmailSender = ""
                    newEmailTags = ""
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}