package com.example.books4me.API.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShitContainer(
    val numFound: Int,
    val start: Int,
    val numFoundExact: Boolean,
    val docs: List<Doc>
)