package com.example.books4me.API

import com.example.books4me.API.dto.Book
import com.example.books4me.API.dto.APIresponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class BookAPI {
    suspend fun foo(): String {
        val client = HttpClient(CIO)
        val response : HttpResponse = client.get("https://openlibrary.org/search.json") {
            url {
                //parameters.append("title", "the+witcher+blood+of+elves")
                parameters.append("title", "moby+dick")
            }
        }
        var stringBody: String = response.body()
        println("Successful response!")
        //println(stringBody)


        val jsonParser = Json {
            ignoreUnknownKeys = true
        }

        val res = jsonParser.decodeFromString<APIresponse>(stringBody)

        val books = res.docs.map {
//            var subject : String? = null
//            if (it.subject_facet != null){
//                subject = it.subject_facet[0]
//            }

            Book(
                authorName = it.author_name?.firstOrNull().orEmpty(),
                publishDate = it.publish_date?.firstOrNull().orEmpty(),
                title = it.title.orEmpty(),
                isbn = it.isbn?.firstOrNull().orEmpty(),
                pages = it.number_of_pages_median,
                coverId = it.cover_i,
                publisher = it.publisher?.firstOrNull().orEmpty(),
                subject = it.subject_facet?.firstOrNull().orEmpty()
            )
        }

        books.forEach { println(it) }

        return stringBody
    }
}