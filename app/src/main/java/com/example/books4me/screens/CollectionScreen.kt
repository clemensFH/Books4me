package com.example.books4me.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.navigation.NavHostController
import com.example.books4me.components.AppBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Books4Me") }
            )
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        CollectionScreenContent(modifier =  Modifier
            .padding(innerPadding)
            .background(Color.Yellow)
            .fillMaxHeight())
    }
}

@Composable
fun CollectionScreenContent(modifier: Modifier) {
    var searchText by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        Row {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Title") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                }
            )
            OutlinedButton(onClick = {
                //  implement search from books added here...
            }) {
                Text(text = "Search")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "search results here...")
            /*if (books.isEmpty()){
                Text(text = "No search results")
            }
            SearchResultList(results = books, modifier = Modifier)*/
        }
    }
}