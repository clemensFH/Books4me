package com.example.books4me.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.R
import com.example.books4me.components.AppBottomNavigation
import com.example.books4me.model.Book
import com.example.books4me.navigation.Screen
import com.example.books4me.viewmodels.CollectionListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(navController: NavHostController, viewModel: CollectionListViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Collection") }
            )
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        CollectionScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Yellow)
                .fillMaxHeight(),
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun CollectionScreenContent(
    modifier: Modifier,
    viewModel: CollectionListViewModel,
    navController: NavHostController
) {
    var searchQuery by remember { mutableStateOf("") }

    val books by viewModel.books.observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    if (searchQuery.isEmpty()) {
        viewModel.clearSearchResult()
    }

    Column(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            TextField(
                value = searchQuery,
                onValueChange =
                {searchQuery = it
                    viewModel.searchBooksByQuery(searchQuery)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Search by Title, Author, Genre, or Date") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(BorderStroke(1.dp, Color.Black))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            var showDelete = true
            var displayBooks = books
            if (searchQuery.isNotEmpty()){
                displayBooks = searchResults
                showDelete = false
            }
            if (displayBooks.isEmpty()) {
                Text(text = "No search results")
            } else {
                LazyColumn {
                    items(displayBooks) { book ->
                        CollectionListItem(
                            book = book,
                            navController = navController,
                            showDelete = showDelete,
                            onDelete = {
                                viewModel.removeFromCollection(it)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CollectionListItem(book: Book, navController: NavHostController, showDelete: Boolean, onDelete: (Book) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Delete Book") },
            text = { Text("Do you really want to delete this book?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(book)
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.BookDetail.createRoute(book.id))
            }
            .border(
                BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column {
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
                if (showDelete){
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Icon")
                    }
                }
            }
        }
    }
}

