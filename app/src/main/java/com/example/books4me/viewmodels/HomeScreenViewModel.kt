package com.example.books4me.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: BookRepository) : ViewModel() {

    fun addToReadlist(book: BookSearchResult) {
        val toAdd = book.toBook()
        toAdd.isInReadlist = true
        viewModelScope.launch {
            repository.insertBook(toAdd)
        }
    }

    fun addToPlanToReadlist(book: BookSearchResult) {
        val toAdd = book.toBook()
        toAdd.isInPlanToReadlist = true
        viewModelScope.launch {
            repository.insertBook(toAdd)
        }
    }

    fun addToCollection(book: BookSearchResult) {
        val toAdd = book.toBook()
        toAdd.isInCollectionlist = true
        viewModelScope.launch {
            repository.insertBook(toAdd)
        }
    }
}

// Extension function to convert BookSearchResult to Book
fun BookSearchResult.toBook(): Book {
    return Book(
        title = this.title,
        authorName = this.authorName,
        subject = this.subject,
        publishDate = this.publishDate,
        publisher = this.publisher,
        pages = this.pages,
        isbn = this.isbn,
        coverId = this.coverId,
        rating = 0,
        comment = "",
        isInReadlist = false,
        isInCollectionlist = false,
        isInPlanToReadlist = false,
    )
}
