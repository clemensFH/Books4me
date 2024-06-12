package com.example.books4me.API.dto

import kotlinx.serialization.Serializable

@Serializable
data class Bookingtion(
    val authorName: String,
    val publishDate: String,
    val title: String?
)