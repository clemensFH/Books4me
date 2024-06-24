package com.example.books4me.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.R
import com.example.books4me.viewmodels.BookViewModel


@Composable
fun SearchResultList(
    results: List<BookSearchResult>,
    navController: NavHostController,
    bookViewModel: BookViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(results) { book ->
            BookItem(bookSearchResult = book, navController = navController, bookViewModel = bookViewModel)
        }
    }
}

@Composable
fun BookItem(bookSearchResult: BookSearchResult, navController: NavHostController, bookViewModel: BookViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = if (bookSearchResult.coverId == null)
                        R.drawable.error
                    else
                        ImageRequest.Builder(LocalContext.current)
                            .data("https://covers.openlibrary.org/b/id/${bookSearchResult.coverId}-L.jpg")
                            .crossfade(true)
                            .build(),
                    placeholder = painterResource(id = R.drawable.loading),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Book Preview Image",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(end = 16.dp)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = bookSearchResult.title ?: "Unknown Title", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text(text = bookSearchResult.authorName ?: "Unknown Author", fontSize = 17.sp)
                    Text(text = bookSearchResult.subject ?: "No Subject", fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        bookViewModel.addToReadList(bookSearchResult)
                        navController.navigate("readlist")
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .height(54.dp)
                        .width(128.dp)
                ) {
                    Text(
                        text = "Readlist",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
                OutlinedButton(
                    onClick = {
                        bookViewModel.addToPlanToRead(bookSearchResult)
                        navController.navigate("plan_to_read")
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .height(54.dp)
                        .width(128.dp)
                ) {
                    Text(
                        text = "Plan-to- Read",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = {
                        bookViewModel.addToCollection(bookSearchResult)
                        navController.navigate("collection")
                    },
                    modifier = Modifier
                        .height(54.dp)
                        .width(128.dp)
                ) {
                    Text(
                        text = "Collection",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }
}