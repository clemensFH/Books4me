package com.example.books4me.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreenContent() {
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