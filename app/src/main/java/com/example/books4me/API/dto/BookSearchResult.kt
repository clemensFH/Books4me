package com.example.books4me.API.dto

data class BookSearchResult(
    val title: String?,
    val authorName: String,
    val subject: String,
    val publishDate: String,
    val publisher: String,
    val pages: Int?,
    val isbn: String,
    val coverId: Long?
)

fun getBackups(): List<BookSearchResult> {
    val b1 = BookSearchResult(
        title = "Lord of the Rings",
        authorName = "J.R.R Tolkien",
        subject = "Fantasy",
        publishDate = "1971",
        publisher = "Scotland Yard",
        pages = 939,
        isbn = "1234-5678-90",
        coverId = null
    )

    val b2 = BookSearchResult(
        title = "Schuld und Sühne",
        authorName = "Dostowjevsky",
        subject = "Classic, Crime",
        publishDate = "1895",
        publisher = "Russia",
        pages = 1240,
        isbn = "0987-6543-21",
        coverId = null
    )

    val b3 = BookSearchResult(
        title = "Stadt aus Glas",
        authorName = "Paul Auster",
        subject = "Thriller",
        publishDate = "1989",
        publisher = "Avana",
        pages = 450,
        isbn = "5341-3678-02",
        coverId = null
    )
    val b4 = BookSearchResult(
        title = "Datenbanken und Konzepte",
        authorName = "Göschka",
        subject = "Science",
        publishDate = "2011",
        publisher = "Colver",
        pages = 650,
        isbn = "6843-1931-67",
        coverId = null
    )

    return listOf(b1, b2, b3, b4)
}