package com.example.emailapp.view.screens.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.emailapp.viewmodel.CalendarViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController) {
    val viewModel: CalendarViewModel = viewModel()
    val events = viewModel.events
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDialog by remember { mutableStateOf(false) }
    var newEvent by remember { mutableStateOf("") }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Month")
                        }
                        Text(
                            text = "${currentMonth.month.name} ${currentMonth.year}",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                        IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Next Month", modifier = Modifier.rotate(180f))
                        }
                    }

                    // Display calendar
                    CalendarView(
                        yearMonth = currentMonth,
                        selectedDate = selectedDate,
                        onDateSelected = {
                            selectedDate = it
                            showDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Display events for the selected date
                    Text(
                        text = "Events",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        events[selectedDate]?.let { eventList ->
                            items(eventList) { event ->
                                EventItem(event) {
                                    events[selectedDate]?.remove(event)
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Event") },
            text = {
                Column {
                    Text("Event on ${selectedDate}")
                    TextField(value = newEvent, onValueChange = { newEvent = it }, placeholder = { Text("Event description") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    events.getOrPut(selectedDate) { mutableListOf() }.add(newEvent)
                    showDialog = false
                    newEvent = ""
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
