package com.example.books4me.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.books4me.API.dto.Book
import com.example.books4me.R

@Composable
fun SearchResultList(results: List<Book>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(results) { book ->
            SearchResultRow(book = book)
        }
    }
}

@Composable
fun SearchResultRow(
    book: Book,
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {}
            .height(200.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Row {
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
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight()
//                    .fillMaxWidth(0.3f) // Set the width to 50% of the parent's width
//                    .aspectRatio(0.66f) // Set the height to 66% of the width
            )
            Column {
                book.title?.let { Text(text = it) }
                Text(text = book.authorName)
                Text(text = book.subject)
            }
        }
    }
}