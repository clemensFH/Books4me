package com.example.books4me.API

import com.example.books4me.API.dto.Bookingtion
import com.example.books4me.API.dto.Doc
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BookAPI {
    suspend fun foo(): String {
        val client = HttpClient(CIO)
        val response : HttpResponse = client.get("https://openlibrary.org/search.json") {
            url {
                parameters.append("title", "the+witcher+blood+of+elves")
            }
        }
        val stringBody: String = response.body()
        println("Successful response!")
        println(stringBody)


        val jsonParser = Json {
            ignoreUnknownKeys = true
        }
//        val res = jsonParser.decodeFromString<Doc>(stringBody)
//
//        val books = res.docs.map {
//            Bookingtion(
//                authorName = it.author_name.firstOrNull().orEmpty(),
//                publishDate = it.publish_date.firstOrNull().orEmpty(),
//                title = it.title
//            )
//        }
//
//        books.forEach { println(it) }

        return stringBody
    }
}