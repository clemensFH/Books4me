package com.example.books4me.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.launch

class BookDetailViewModel(private val repository: BookRepository) : ViewModel() {

    fun getBookById(bookId: Long) = repository.getBookById(bookId)

    fun addToReadList(book: Book) {
        book.isInCollectionlist = false
        book.isInPlanToReadlist = false
        book.isInReadlist = true
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun addToPlanToRead(book: Book) {
        book.isInCollectionlist = false
        book.isInPlanToReadlist = true
        book.isInReadlist = false
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun addToCollection(book: Book) {
        book.isInCollectionlist = true
        book.isInPlanToReadlist = false
        book.isInReadlist = false
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun updateBookRating(book: Book, newRating: Int) {
        viewModelScope.launch {
            book.rating = newRating
            repository.updateBook(book)
        }
    }

    fun saveComment(book: Book, comment: String) {
        viewModelScope.launch {
            book.comment = comment
            repository.updateBook(book)
        }
    }
}
