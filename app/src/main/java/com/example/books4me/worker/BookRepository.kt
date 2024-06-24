package com.example.books4me.worker

import com.example.books4me.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateMovie(book: Book) = bookDao.update(book)

    suspend fun deleteMovie(book: Book) = bookDao.delete(book)
    suspend fun getBookById(id: Long) = bookDao.getById(id)

    fun getBooksInCollectionlist(): Flow<List<Book>> = bookDao.getBooksInCollectionlist()

    fun getBooksInPlanToReadlist(): Flow<List<Book>> = bookDao.getBooksInReadlist()

    fun getBooksInReadlist(): Flow<List<Book>> = bookDao.getBooksInReadlist()
}