package com.example.books4me.API

import com.example.books4me.API.dto.Bookingtion
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
                parameters.append("title", "the+lord+of+the+rings")
            }
        }
        var stringBody: String = response.body()
        println("Successful response!")
        println(stringBody)


        val jsonParser = Json {
            ignoreUnknownKeys = true
        }

//        stringBody = """
//            {
//                "numFound": 2,
//                "start": 0,
//                "numFoundExact": true,
//                "docs": [
//                    {
//                        "author_alternative_name": [
//                            "Andrzej sapkowski",
//                            "And\u017eeja Sapkovskog",
//                            "Andrej Sapkowski",
//                            "Andrzei Sapkowski",
//                            "Andrejz Sapkowski"
//                        ],
//                        "author_key": [
//                            "OL368638A"
//                        ],
//                        "author_name": [
//                            "Andrzej Sapkowski"
//                        ],
//                        "cover_edition_key": "OL50523882M",
//                        "cover_i": 14560445,
//                        "ebook_access": "no_ebook",
//                        "ebook_count_i": 0,
//                        "edition_count": 1,
//                        "edition_key": [
//                            "OL50523882M"
//                        ],
//                        "first_publish_year": 2021,
//                        "first_sentence": [
//                            "The town was in flames."
//                        ],
//                        "format": [
//                            "Paperback"
//                        ],
//                        "has_fulltext": false,
//                        "isbn": [
//                            "031639209X",
//                            "9780316392099"
//                        ],
//                        "key": "/works/OL37501425W",
//                        "last_modified_i": 1703764986,
//                        "number_of_pages_median": 424,
//                        "public_scan_b": false,
//                        "publish_date": [
//                            "December 2021"
//                        ],
//                        "publish_year": [
//                            2021
//                        ],
//                        "publisher": [
//                            "Arrangement with the Patricia pasqualini literary agency"
//                        ],
//                        "seed": [
//                            "/books/OL50523882M",
//                            "/works/OL37501425W",
//                            "/authors/OL368638A"
//                        ],
//                        "title": "The Witcher. Blood of elves",
//                        "title_suggest": "The Witcher. Blood of elves",
//                        "title_sort": "The Witcher. Blood of elves",
//                        "type": "work",
//                        "readinglog_count": 1,
//                        "want_to_read_count": 0,
//                        "currently_reading_count": 1,
//                        "already_read_count": 0,
//                        "publisher_facet": [
//                            "Arrangement with the Patricia pasqualini literary agency"
//                        ],
//                        "_version_": 1795915481688834050,
//                        "author_facet": [
//                            "OL368638A Andrzej Sapkowski"
//                        ]
//                    }
//                ],
//                "num_found": 2,
//                "q": "",
//                "offset": null
//            }
//        """.trimIndent()

        val res = jsonParser.decodeFromString<APIresponse>(stringBody)

        val books = res.docs.map {
            Bookingtion(
                authorName = it.author_name?.firstOrNull().orEmpty(),
                publishDate = it.publish_date?.firstOrNull().orEmpty(),
                title = it.title
            )
        }

        books.forEach { println(it) }

        return stringBody
    }
}