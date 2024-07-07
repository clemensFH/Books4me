package com.example.books4me.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String?,
    var authorName: String,
    var subject: String,
    var publishDate: String,
    var publisher: String,
    var pages: Int?,
    var isbn: String,
    var coverId: Long?,
    var rating: Int,
    var comment: String,
    var isInReadlist: Boolean = false,
    var isInCollectionlist: Boolean = false,
    var isInPlanToReadlist: Boolean = false,
    var bookRating: Int
)


    /*{
    constructor(
        id: Long,
        title: String?,
        authorName: String,
        subject: String,
        publishDate: String,
        publisher: String,
        pages: Int?,
        isbn: String,
        coverId: Long?,
        rating: Int,
        comment: String
    ) : this(
        id = id,
        title = title,
        authorName = authorName,
        subject = subject,
        publishDate = publishDate,
        publisher = publisher,
        pages = pages,
        isbn = isbn,
        coverId = coverId,
        rating = rating,
        comment = comment
    )
}*/