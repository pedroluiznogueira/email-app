package com.example.emailapp.view.screens.emaillist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.emailapp.viewmodel.EmailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailListScreen(navController: NavHostController) {
    val viewModel: EmailViewModel = viewModel()
    val emails = viewModel.emails

    var searchQuery by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("All") }

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

        Button(onClick = { navController.navigate("calendar") }) {
            Text("Go to Calendar")
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
                        val index = emails.indexOf(email)
                        viewModel.emails[index] = email.copy(isImportant = !email.isImportant)
                    }
                )
            }
        }
    }
}
