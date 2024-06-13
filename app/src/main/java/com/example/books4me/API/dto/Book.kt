package com.example.books4me.API.dto

import kotlinx.serialization.Serializable

//@Serializable
data class Book(
    val title: String?,
    val authorName: String,
    val subject: String,
    val publishDate: String,
    val publisher: String,
    val pages: Int?,
    val isbn: String,
    val coverId: Long?,
)