package com.example.books4me.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.books4me.API.BookServiceImpl
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.components.AppBottomNavigation
import com.example.books4me.components.SearchResultList
import com.example.books4me.viewmodels.BookViewModel
import com.example.books4me.viewmodels.BookViewModelFactory
import com.example.books4me.viewmodels.HomeScreenViewModel
import com.example.books4me.viewmodels.ReadlistViewModel
import com.example.books4me.worker.BookDatabase
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel) {
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
        HomeScreenContent(
            Modifier
                .padding(innerPadding)
                .background(Color.Yellow)
                .fillMaxHeight(),
            navController,
            viewModel
        )
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel
) {
    var searchText by remember { mutableStateOf("") }
    var books by remember { mutableStateOf(emptyList<BookSearchResult>()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val bookService = BookServiceImpl()

    Column(modifier = modifier) {
        Row {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                placeholder = { Text("Title") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                }
            )
            OutlinedButton(onClick = {
                isLoading = true
                errorMessage = null
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val result = bookService.searchBooksByTitle(searchText)
                        withContext(Dispatchers.Main) {
                            books = result
                            isLoading = false
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            isLoading = false
                            errorMessage = "Failed to load books: ${e.message}"
                        }
                    }
                }
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
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    Text(text = errorMessage ?: "Unknown error")
                }
                books.isEmpty() -> {
                    Text(text = "No search results")
                }
                else -> {
                    SearchResultList(
                        results = books,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}