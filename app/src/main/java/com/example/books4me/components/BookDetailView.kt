package com.example.books4me.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.R
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.model.Book
import com.example.books4me.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(navController: NavHostController, bookId: Long, viewModel: HomeScreenViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val book by viewModel.getBookById(bookId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        book?.let { b ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = if (b.coverId == null)
                                R.drawable.error
                            else
                                ImageRequest.Builder(LocalContext.current)
                                    .data("https://covers.openlibrary.org/b/id/${b.coverId}-L.jpg")
                                    .crossfade(true)
                                    .build(),
                            placeholder = painterResource(id = R.drawable.loading),
                            contentDescription = "Book Cover Image",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(end = 16.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Title: ${b.title}",
                                fontSize = 19.sp,
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = "Author: ${b.authorName}",
                                fontSize = 17.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonSection(book = b, viewModel = viewModel, navController = navController)
                }
            }
        } ?: Text(
            text = "Loading book details...",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun ButtonSection(book: Book, viewModel: HomeScreenViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.addToReadlist(book.toBookSearchResult())
                    navController.navigate("readlist")
                }
            },
            modifier = Modifier
                .padding(4.dp)
                .height(54.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Add to Readlist",
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
            )
        }
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.addToPlanToReadlist(book.toBookSearchResult())
                    navController.navigate("plan_to_read")
                }
            },
            modifier = Modifier
                .padding(4.dp)
                .height(54.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Add to Plan to Read",
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
            )
        }
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.addToCollection(book.toBookSearchResult())
                    navController.navigate("collection")
                }
            },
            modifier = Modifier
                .padding(4.dp)
                .height(54.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Add to Collection",
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp)
            )
        }
    }
}

fun Book.toBookSearchResult(): BookSearchResult {
    return BookSearchResult(
        title = this.title,
        authorName = this.authorName,
        subject = this.subject,
        publishDate = this.publishDate,
        publisher = this.publisher,
        pages = this.pages,
        isbn = this.isbn,
        coverId = this.coverId
    )
}
