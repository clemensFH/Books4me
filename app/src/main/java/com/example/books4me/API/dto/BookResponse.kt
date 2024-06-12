package com.example.books4me.API.dto

data class BookResponse(
    val title : String,
    val author : String,
    val publisher : String,
    val publishYear : Int,
    val pages : Int,
    val isbn : String,
    val coverId : String,
)