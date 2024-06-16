package com.example.emailapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.emailapp.ui.theme.EmailAppTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailAppTheme {
                AppNavigation()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Email App", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                EmailListScreen(navController)
            }
        }
    )
}

@Composable
fun EmailListScreen(navController: NavHostController) {
    var emails by remember { mutableStateOf(listOf(
        Email(1, "Meeting Schedule", "boss@example.com", true, listOf("Work")),
        Email(2, "Lunch with Friends", "friend@example.com", false, listOf("Personal")),
        Email(3, "Project Update", "colleague@example.com", false, listOf("Work")),
        Email(4, "Invitation to Event", "event@invite.com", false, listOf("Events")),
        Email(5, "Newsletter", "newsletter@example.com", false, listOf("Promotions")),
    )) }

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
                        emails = emails.map {
                            if (it.id == email.id) it.copy(isImportant = !it.isImportant) else it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = { /* If needed, handle a specific
            search action that needs to be triggered only
            when the search icon is pressed */ }
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Box(Modifier.fillMaxWidth()) {
                    if (query.isEmpty()) {
                        Text("Search emails", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun FilterDropdown(selectedTag: String, onTagSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val tags = listOf("All", "Work", "Personal", "Events", "Promotions")

    Box(
        modifier = Modifier
            .width(120.dp)
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

@Composable
fun EmailItem(email: Email, onImportantToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = if (email.isImportant) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Important",
                    tint = Color.Yellow,
                    modifier = Modifier.clickable { onImportantToggle() }
                )
            }
            Text(
                text = email.sender,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (email.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    email.tags.forEach { tag ->
                        Text(
                            text = tag,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController) {
    val events = remember { mutableStateMapOf<LocalDate, MutableList<String>>() }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDialog by remember { mutableStateOf(false) }
    var newEvent by remember { mutableStateOf("") }

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
                Column {
                    // Display calendar
                    CalendarView(
                        selectedDate = selectedDate,
                        onDateSelected = {
                            selectedDate = it
                            showDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Display events for the selected date
                    events[selectedDate]?.forEach { event ->
                        Text(text = event, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(8.dp))
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

@Composable
fun CalendarView(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    // Here, you can use a library or build your own simple calendar view
    // For demonstration, let's use a simple text-based month view

    Column {
        Text(text = selectedDate.month.name, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(8.dp))
        for (day in 1..selectedDate.lengthOfMonth()) {
            val date = LocalDate.of(selectedDate.year, selectedDate.month, day)
            Text(
                text = date.dayOfMonth.toString(),
                modifier = Modifier
                    .clickable { onDateSelected(date) }
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EmailAppTheme {
        AppNavigation()
    }
}
