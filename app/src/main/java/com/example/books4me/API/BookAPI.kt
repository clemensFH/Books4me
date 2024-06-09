package com.example.books4me.API

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.*

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
        //println(stringBody)
        return stringBody
    }
}