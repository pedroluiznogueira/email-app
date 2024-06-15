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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emailapp.ui.theme.EmailAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailAppTheme {
                MyApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
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
                EmailListScreen()
            }
        }
    )
}

@Composable
fun EmailListScreen() {
    var emails by remember { mutableStateOf(listOf(
        Email(1, "Meeting Schedule", "boss@example.com", true, listOf("Work")),
        Email(2, "Lunch with Friends", "friend@example.com", false, listOf("Personal")),
        Email(3, "Project Update", "colleague@example.com", false, listOf("Work")),
        Email(4, "Invitation to Event", "event@invite.com", false, listOf("Events")),
        Email(5, "Newsletter", "newsletter@example.com", false, listOf("Promotions")),
        // Add more emails as needed
    )) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(emails) { email ->
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
                Text(text = email.subject, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                Icon(
                    imageVector = if (email.isImportant) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Important",
                    tint = Color.Yellow,
                    modifier = Modifier.clickable { onImportantToggle() }
                )
            }
            Text(text = email.sender, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EmailAppTheme {
        MyApp()
    }
}
