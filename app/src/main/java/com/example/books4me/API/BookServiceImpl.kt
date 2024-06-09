package com.example.books4me.API

import com.example.books4me.API.dto.BookResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class BookServiceImpl(
    private val client: HttpClient
) : BookService {
    override suspend fun getBooks(title: String): List<BookResponse> {
        val response: HttpResponse = client.get(HttpRoutes.BOOKS) {
            url {
                parameters.append("title", title)
            }
        }

        return emptyList<BookResponse>()
    }

    override suspend fun getCover(): String {
        TODO("Not yet implemented")
    }
}