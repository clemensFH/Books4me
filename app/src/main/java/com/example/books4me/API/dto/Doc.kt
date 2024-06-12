package com.example.books4me.API.dto

import kotlinx.serialization.Serializable

@Serializable
data class Doc(
    val numFound: Int,
    val start: Int,
    val numFoundExact: Boolean,
    val docs: List<BookResponse>
)