package com.example.books4me.API

import com.example.books4me.API.dto.Book
import com.example.books4me.API.dto.BookResponse

interface BookService {

    suspend fun searchBooksByTitle(title : String): List<Book>

    suspend fun getCover(): String
}