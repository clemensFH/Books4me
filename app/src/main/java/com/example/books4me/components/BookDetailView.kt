package com.example.books4me.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.R
import com.example.books4me.model.Book
import com.example.books4me.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(
    navController: NavHostController,
    bookId: Long,
    viewModel: HomeScreenViewModel = viewModel()
) {
    rememberCoroutineScope()
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
            var bookRating by remember { mutableIntStateOf(b.bookRating) }
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
                            Text(
                                text = "Genres: ${b.subject}",
                                fontSize = 17.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonSection(book = b, viewModel = viewModel, navController = navController)
                    RatingSection(onRatingChange = { newRating ->
                        bookRating = newRating
                        viewModel.updateBookRating(b, newRating)
                    })
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
fun ReadlistButton(
    modifier: Modifier = Modifier,
    book: Book,
    viewModel: HomeScreenViewModel,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                viewModel.addToReadlist(book.toBookSearchResult())
                navController.navigate("readlist")
            }
        },
        modifier
            .padding(4.dp)
            .height(54.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Add to Readlist",
            style = TextStyle(fontSize = 18.sp)
        )
    }
}

/*@Composable
fun PlanToReadButton(modifier: Modifier = Modifier, book: Book, viewModel: HomeScreenViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                viewModel.addToPlanToReadlist(book.toBookSearchResult())
                navController.navigate("plan_to_read")
            }
        },
        modifier
            .padding(4.dp)
            .height(54.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Add to Plan to Read",
            style = TextStyle(fontSize = 18.sp)
        )
    }
}*/

@Composable
fun CollectionButton(
    modifier: Modifier = Modifier,
    book: Book,
    viewModel: HomeScreenViewModel,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                viewModel.addToCollection(book.toBookSearchResult())
                navController.navigate("collection")
            }
        },
        modifier
            .padding(4.dp)
            .height(54.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Add to Collection",
            style = TextStyle(fontSize = 18.sp)
        )
    }
}

@Composable
fun ButtonSection(book: Book, viewModel: HomeScreenViewModel, navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (navController.currentDestination?.route == "readlist") {
            CollectionButton(book = book, viewModel = viewModel, navController = navController)
        }

        if (navController.currentDestination?.route == "plan_to_read") {
            ReadlistButton(book = book, viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun RatingSection(onRatingChange: (Int) -> Unit) {
    Column {
        var userRating by remember { mutableIntStateOf(0) }
        Row {
            for (i in 1..5) {
                IconButton(onClick = {
                    userRating = i
                    onRatingChange(userRating)
                }) {
                    Icon(
                        imageVector = if (i <= userRating) {
                            Icons.Filled.Star
                        } else {
                            Icons.Outlined.Star
                        }, contentDescription = "Stars reflecting user rating",
                        tint = if (i <= userRating) {
                            Color.Yellow
                        } else {
                            Color.Unspecified
                        }
                    )
                }
            }

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
