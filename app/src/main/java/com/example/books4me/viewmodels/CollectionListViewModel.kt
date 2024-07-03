package com.example.books4me.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CollectionListViewModel(private val repository: BookRepository) : ViewModel(){
    private val _books = MutableStateFlow(listOf<Book>())
    val books: StateFlow<List<Book>> = _books.asStateFlow()
    private val _searchResults = MutableStateFlow(listOf<Book>())
    val searchResults: StateFlow<List<Book>> = _searchResults.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getBooksInCollectionlist().collect{
                _books.value = it
            }
        }
    }

    fun removeFromCollection(book: Book){
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun moveToReadlist(book: Book){
        book.isInCollectionlist = false
        book.isInReadlist = true
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun moveToPlanToRead(book: Book){
        book.isInCollectionlist = false
        book.isInPlanToReadlist = true
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun searchBooksByTitle(query: String) {
        val filteredBooks = if (query.isBlank()) {
            emptyList()
        } else {
            _books.value.filter { book ->
                book.title?.contains(query, ignoreCase = true) ?: false
            }
        }
        _searchResults.value = filteredBooks
    }
}