package com.example.books4me.API

import com.example.books4me.API.dto.BookResponse

interface BookService {

    suspend fun getBooks(title : String): List<BookResponse>

    suspend fun getCover(): String
}