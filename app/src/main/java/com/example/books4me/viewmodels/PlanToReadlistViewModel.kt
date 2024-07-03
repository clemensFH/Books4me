package com.example.books4me.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlanToReadlistViewModel(private val repository: BookRepository) : ViewModel(){
    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>> = _books
    private val _searchResults = MutableLiveData<List<Book>>(emptyList())
    val searchResults: LiveData<List<Book>> = _searchResults

    init {
        viewModelScope.launch {
            repository.getBooksInPlanToReadlist().collect{
                _books.value = it
            }
        }
    }

    fun removeFromPlanToRead(book: Book){
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun moveToReadlist(book: Book){
        book.isInPlanToReadlist = false
        book.isInReadlist = true
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun moveToCollection(book: Book){
        book.isInPlanToReadlist = false
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
}