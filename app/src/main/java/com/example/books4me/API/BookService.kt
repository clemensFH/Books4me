package com.example.books4me.API

import com.example.books4me.API.dto.BookSearchResult

interface BookService {

    suspend fun searchBooksByTitle(title : String): List<BookSearchResult>
}