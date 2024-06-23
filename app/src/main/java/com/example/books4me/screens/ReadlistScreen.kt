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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.API.BookServiceImpl
import com.example.books4me.API.dto.Book
import com.example.books4me.R
import com.example.books4me.components.AppBottomNavigation
import com.example.books4me.viewmodels.BookViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadlistScreen(navController: NavHostController, bookViewModel: BookViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ReadList") }
            )
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        ReadlistScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Yellow)
                .fillMaxHeight(), bookViewModel
        )
    }
}

@Composable
fun ReadlistScreenContent(modifier: Modifier, bookViewModel: BookViewModel) {
    var searchText by remember { mutableStateOf("") }
    var books by remember { mutableStateOf(emptyList<Book>()) }
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
                placeholder = { Text("Search...") },
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
                    LazyColumn {
                        items(books) { book ->
                            ReadListItem(book)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReadListItem(book: Book) {
    println(book)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = if (book.coverId == null)
                    R.drawable.error
                else
                    ImageRequest.Builder(LocalContext.current)
                        .data("https://covers.openlibrary.org/b/id/${book.coverId}-L.jpg")
                        .crossfade(true)
                        .build(),
                placeholder = painterResource(id = R.drawable.loading),
                contentScale = ContentScale.Crop,
                contentDescription = "Book Preview Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = 8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title ?: "Unknown Title")
                Text(text = book.authorName ?: "Unknown Author")
                Text(text = book.subject ?: "No Subject")
            }
        }
    }
}