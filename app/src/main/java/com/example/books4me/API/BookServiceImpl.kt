package com.example.books4me.API

import com.example.books4me.API.dto.APIresponse
import com.example.books4me.API.dto.BookSearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

class BookServiceImpl : BookService {

    private val jsonParser = Json {
        ignoreUnknownKeys = true
    }

    private val client = HttpClient(CIO)

    private fun concatFirstThree(list: List<String>): String {
        return when (list.size) {
            0 -> ""
            1 -> list[0]
            2 -> "${list[0]}, ${list[1]}"
            else -> "${list[0]}, ${list[1]}, ${list[2]}"
        }
    }

    override suspend fun searchBooksByTitle(title: String): List<BookSearchResult> {
        val response: HttpResponse = client.get(HttpRoutes.BOOKS) {
            url {
                parameters.append("title", title)
            }
        }

        val bookResponses = jsonParser.decodeFromString<APIresponse>(response.body())

        val books = bookResponses.docs.map {
            BookSearchResult(
                authorName = it.author_name?.firstOrNull().orEmpty(),
                publishDate = it.publish_date?.firstOrNull().orEmpty(),
                title = it.title.orEmpty(),
                isbn = it.isbn?.firstOrNull().orEmpty(),
                pages = it.number_of_pages_median,
                coverId = it.cover_i,
                publisher = it.publisher?.firstOrNull().orEmpty(),
                subject = if (it.subject_facet != null) concatFirstThree(it.subject_facet) else ""
            )
        }

        return books
    }
}