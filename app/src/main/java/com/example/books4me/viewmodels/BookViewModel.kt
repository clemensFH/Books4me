package com.example.books4me.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.books4me.API.dto.Book

class BookViewModel : ViewModel() {
    private val readList = mutableStateListOf<Book>()
    private val planToReadList = mutableStateListOf<Book>()
    private val collectionList = mutableStateListOf<Book>()

    fun addToReadList(book: Book) {
        if (!readList.contains(book)) {
            readList.add(book)
        }
    }

    fun addToPlanToRead(book: Book) {
        if (!planToReadList.contains(book)) {
            planToReadList.add(book)
        }
    }

    fun addToCollection(book: Book) {
        if (!collectionList.contains(book)) {
            collectionList.add(book)
        }
    }

    fun getReadList() = readList
    fun getPlanToReadList() = planToReadList
    fun getCollectionList() = collectionList
}
