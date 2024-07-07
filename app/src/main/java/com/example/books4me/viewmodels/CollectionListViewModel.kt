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

class CollectionListViewModel(private val repository: BookRepository) : ViewModel(){
    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>> = _books
    private val _searchResults = MutableLiveData<List<Book>>(emptyList())
    val searchResults: LiveData<List<Book>> = _searchResults

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
            _books.value?.filter { book ->
                book.title?.contains(query, ignoreCase = true) ?: false
            } ?: emptyList()
        }
        _searchResults.value = filteredBooks
    }
}
