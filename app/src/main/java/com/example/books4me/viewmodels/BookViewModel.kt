package com.example.books4me.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.books4me.API.dto.BookSearchResult

class BookViewModel : ViewModel() {
    private val readList = mutableStateListOf<BookSearchResult>()
    private val planToReadList = mutableStateListOf<BookSearchResult>()
    private val collectionList = mutableStateListOf<BookSearchResult>()

    fun addToReadList(book: BookSearchResult) {
        if (!readList.contains(book)) {
            readList.add(book)
        }
    }

    fun addToPlanToRead(book: BookSearchResult) {
        if (!planToReadList.contains(book)) {
            planToReadList.add(book)
        }
    }

    fun addToCollection(book: BookSearchResult) {
        if (!collectionList.contains(book)) {
            collectionList.add(book)
        }
    }

    fun getReadList() = readList
    fun getPlanToReadList() = planToReadList
    fun getCollectionList() = collectionList
}
