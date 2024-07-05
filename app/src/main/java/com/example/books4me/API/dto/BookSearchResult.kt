package com.example.books4me.API.dto

data class BookSearchResult(
    val id: Long,
    val title: String?,
    val authorName: String,
    val subject: String,
    val publishDate: String,
    val publisher: String,
    val pages: Int?,
    val isbn: String,
    val coverId: Long?
)