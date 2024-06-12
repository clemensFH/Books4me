package com.example.books4me.API.dto
import kotlinx.serialization.*

@Serializable
data class BookResponse(
//    val author_alternative_name : List<String>? = null,
//    val author_key : List<String>? = null,
    val author_name : List<String>? = null,
//    val cover_edition_key : String? = null,
    val cover_i : Int? = null,
//    val ebook_access : String? = null,
//    val ebook_count_i : Int? = null,
//    val edition_count : Int? = null,
//    val edition_key : List<String>? = null,
//    val first_publish_year : Int? = null,
//    val first_sentence : List<String>? = null,
//    val format : List<String>? = null,
//    val has_fulltext : Boolean? = null,
    val isbn : List<String>? = null,
//    val key : String? = null,
//    val last_modified_i : Int? = null,
    val number_of_pages_median : Int? = null,
//    val public_scan_b : Boolean? = null,
    val publish_date: List<String>? = null,
//    val publish_year : List<Int>? = null,
//    val publisher : List<String>? = null,
//    val seed : List<String>? = null,
    val title : String? = null,
//    val title_suggest : String? = null,
//    val title_sort : String? = null,
//    val type : String? = null,
//    val readinglog_count : Int? = null,
//    val want_to_read_count : Int? = null,
//    val currently_reading_count : Int? = null,
//    val already_read_count : Int? = null,
//    val publisher_facet : List<String>? = null,
//    val _version_ : Long? = null,
//    val author_facet : List<String>? = null
)