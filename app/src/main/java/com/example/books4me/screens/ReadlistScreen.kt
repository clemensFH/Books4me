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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.R
import com.example.books4me.components.AppBottomNavigation
import com.example.books4me.model.Book
import com.example.books4me.navigation.Screen
import com.example.books4me.viewmodels.ReadlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadlistScreen(navController: NavHostController, viewModel: ReadlistViewModel) {
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
                .fillMaxHeight(), viewModel,
            navController = navController
        )
    }
}

@Composable
fun ReadlistScreenContent(modifier: Modifier, viewModel: ReadlistViewModel, navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }

    val books by viewModel.books.observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

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
                viewModel.searchBooksByTitle(searchText)
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
            val displayBooks = if (searchText.isBlank()) {
                books
            } else {
                searchResults
            }
            if (displayBooks.isEmpty()) {
                Text(text = "No search results")
            } else {
                LazyColumn {
                    items(displayBooks) { book ->
                        ReadListItem(book, onButtonClick = { bookToMove ->
                            viewModel.moveToCollection(bookToMove)
                        }, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ReadListItem(book: Book, onButtonClick: (Book) -> Unit, navController: NavHostController) {
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
            }
            OutlinedButton(
                onClick = { onButtonClick(book) },
                modifier = Modifier
                    .padding(4.dp)
                    .height(54.dp)
                    .width(128.dp)
            ) {
                Text(
                    text = "Remove",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
            OutlinedButton(
                onClick = { onButtonClick(book) },
                modifier = Modifier
                    .padding(4.dp)
                    .height(54.dp)
                    .width(128.dp)
            ) {
                Text(
                    text = "To Collection",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}
