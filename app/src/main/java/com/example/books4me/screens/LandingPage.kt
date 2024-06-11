package com.example.books4me.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPageScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Books4Me") }
                )
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = { Text("Search...") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigation(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.Yellow)
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (selectedItem) {
                0 -> HomeScreenContent()
                1 -> Text(
                    text = "Readlist Screen",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                2 -> Text(
                    text = "Plan-to-Read Screen",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                3 -> Text(
                    text = "Collection Screen",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                else -> Unit
            }
        }
    }
}

@Composable
fun HomeScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Column {
            Text(text = "Search results here...")
            Text(text = ".")
            Text(text = ".")
            Text(text = ".")
        }
    }
}

@Composable
fun BottomNavigation(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Readlist") },
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            label = { Text("Readlist") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ArrowForward, contentDescription = "Plan-to-Read") },
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            label = { Text("Plan-to-Read") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Done, contentDescription = "Collection") },
            selected = selectedItem == 3,
            onClick = { onItemSelected(3) },
            label = { Text("Collection") }
        )
    }
}

@Composable
fun NavigationBarItem(
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    NavigationBarItem(
        icon = icon,
        selected = selected,
        onClick = onClick,
        label = label
    )
}
