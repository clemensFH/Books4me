package com.example.books4me.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.books4me.API.dto.Book
import com.example.books4me.R
import com.example.books4me.viewmodels.BookViewModel


@Composable
fun SearchResultList(
    results: List<Book>,
    navController: NavHostController,
    bookViewModel: BookViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(results) { book ->
            BookItem(book = book, navController = navController, bookViewModel = bookViewModel)
        }
    }
}

@Composable
fun BookItem(book: Book, navController: NavHostController, bookViewModel: BookViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(1.dp, Color.Gray))
            .padding(8.dp)
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
                    .size(100.dp)
                    .padding(end = 8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title ?: "Unknown Title")
                Text(text = book.authorName ?: "Unknown Author")
                Text(text = book.subject ?: "No Subject")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    OutlinedButton(
                        onClick = {
                            bookViewModel.addToReadList(book)
                            navController.navigate("readlist")
                        },
                        /*modifier = Modifier
                            .padding(end = 4.dp)
                            .height(20.dp)
                            .width(40.dp)*/
                    ) {
                        Text(text = "readlist")
                    }
                    OutlinedButton(
                        onClick = {
                            bookViewModel.addToPlanToRead(book)
                            navController.navigate("plan_to_read")
                        },
                        /*modifier = Modifier
                            .padding(end = 4.dp)
                            .height(20.dp)
                            .width(40.dp)*/
                    ) {
                        Text ( text = "Plan-to-Read")
                    }
                    OutlinedButton(
                        onClick = {
                            bookViewModel.addToCollection(book)
                            navController.navigate("collection")
                        },
                        /*modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)*/
                    ) {
                        Text ( text=  "Collection")
                    }
                }
            }
        }
    }
}