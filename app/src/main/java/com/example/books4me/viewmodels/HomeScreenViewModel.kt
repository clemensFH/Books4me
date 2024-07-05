package com.example.books4me.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: BookRepository) : ViewModel() {

    fun getBookById(bookId: Long) = repository.getBookById(bookId)

    fun addToReadlist(book: BookSearchResult) {
        val toAdd = book.toBook()
        viewModelScope.launch {
            repository.insertBook(toAdd.copy(isInReadlist = true))
        }
    }

    fun addToPlanToReadlist(book: BookSearchResult) {
        val toAdd = book.toBook()
        viewModelScope.launch {
            repository.insertBook(toAdd.copy(isInPlanToReadlist = true))
        }
    }

    fun addToCollection(book: BookSearchResult) {
        val toAdd = book.toBook()
        viewModelScope.launch {
            repository.insertBook(toAdd.copy(isInCollectionlist = true))
        }
    }
}

// Extension function to convert BookSearchResult to Book
fun BookSearchResult.toBook(): Book {
    return Book(
        id = this.id,
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
        isInPlanToReadlist = false
    )
}
