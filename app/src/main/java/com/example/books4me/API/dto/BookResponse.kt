package com.example.books4me.API.dto
import kotlinx.serialization.*

@Serializable
data class BookResponse(
    val author_alternative_name : List<String>,
    val author_key : List<String>,
    val author_name : List<String>,
    val cover_edition_key : String,
    val cover_i : Int,
    val ebook_access : String,
    val ebook_count_i : Int,
    val edition_count : Int,
    val edition_key : List<String>,
    val first_publish_year : Int,
    val first_sentence : List<String>,
    val format : List<String>,
    val has_fulltext : Boolean,
    val isbn : List<String>,
    val key : String,
    val last_modified_i : Int,
    val number_of_pages_median : Int,
    val public_scan_b : Boolean,
    val publish_date: List<String>,
    val publish_year : List<Int>,
    val publisher : List<String>,
    val seed : List<String>,
    val title : String,
    val title_suggest : String,
    val title_sort : String,
    val type : String,
    val readinglog_count : Int,
    val want_to_read_count : Int,
    val currently_reading_count : Int,
    val already_read_count : Int,
    val publisher_facet : List<String>,
    val _version : Int,
    val author_facet : List<String>
)