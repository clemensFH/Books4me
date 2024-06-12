package com.example.books4me.API

object HttpRoutes {

    private const val BOOKS_BASE_URL = "https://openlibrary.org"
    private const val COVERS_BASE_URL = "https://covers.openlibrary.org"

    const val BOOKS = "$BOOKS_BASE_URL/search.json"
    const val COVERS = "$COVERS_BASE_URL/id/"

}