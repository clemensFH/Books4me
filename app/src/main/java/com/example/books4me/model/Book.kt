package com.example.books4me.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String?,
    val authorName: String,
    val subject: String,
    val publishDate: String,
    val publisher: String,
    val pages: Int?,
    val isbn: String,
    val coverId: Long?,
    val rating: Int,
    val comment: String,
    val isInReadlist: Boolean,
    val isInCollectionlist: Boolean,
    val isInPlanToReadlist: Boolean
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