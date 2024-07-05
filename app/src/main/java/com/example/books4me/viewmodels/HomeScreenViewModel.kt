package com.example.books4me.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books4me.API.dto.BookSearchResult
import com.example.books4me.model.Book
import com.example.books4me.worker.BookRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: BookRepository) : ViewModel(){
    fun addToReadlist(book: BookSearchResult){
        val toAdd = GetBookFromSearchResult(book)

        viewModelScope.launch {
            repository.insertBook(toAdd, "readlist")
        }
    }

    fun addToCollection(book: BookSearchResult){
        val toAdd = GetBookFromSearchResult(book)

        viewModelScope.launch {
            repository.insertBook(toAdd, "collection")
        }
    }

    fun addToPlanToReadlist(book: BookSearchResult){
        val toAdd = GetBookFromSearchResult(book)

        viewModelScope.launch {
            repository.insertBook(toAdd, "plan_to_read")
        }
    }

    private fun GetBookFromSearchResult(book: BookSearchResult): Book {
        val toAdd = Book(
            title = book.title,
            authorName = book.authorName,
            subject = book.subject,
            publishDate = book.publishDate,
            publisher = book.publisher,
            pages = book.pages,
            isbn = book.isbn,
            coverId = book.coverId,
            rating = 0,
            comment = "",
            isInReadlist = true,
            isInCollectionlist = false,
            isInPlanToReadlist = false
        )
        return toAdd
    }
}