package com.example.books4me.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReadlistViewModel(private val repository: BookRepository) : ViewModel() {
    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>> = _books
    private val _searchResults = MutableLiveData<List<Book>>(emptyList())
    val searchResults: LiveData<List<Book>> = _searchResults

    init {
        viewModelScope.launch {
            repository.getBooksInReadlist().collect {
                _books.value = it
            }
        }
    }

    fun removeFromReadlist(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
            _books.value = _books.value?.filter { it.id != book.id }
        }
    }

    fun moveToPlanToRead(book: Book) {
        book.isInReadlist = false
        book.isInPlanToReadlist = true
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun moveToCollection(book: Book) {
        book.isInReadlist = false
        book.isInCollectionlist = true
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun searchBooksByTitle(query: String) {
        val filteredBooks = if (query.isBlank()) {
            emptyList()
        } else {
            _books.value?.filter { book ->
                book.title?.contains(query, ignoreCase = true) ?: false
            } ?: emptyList()
        }
        _searchResults.value = filteredBooks
    }

    fun searchBooksByQuery(query: String) {
        val filteredBooks = _books.value?.filter { book ->
            book.title?.contains(query, ignoreCase = true) == true ||
                    book.authorName?.contains(query, ignoreCase = true) == true ||
                    book.subject?.contains(query, ignoreCase = true) == true ||
                    book.publishDate?.contains(query, ignoreCase = true) == true
        } ?: emptyList()
        _searchResults.value = filteredBooks
    }

    fun removeBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
}
