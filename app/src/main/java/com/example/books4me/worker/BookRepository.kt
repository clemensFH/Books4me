package com.example.books4me.worker

import com.example.books4me.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateBook(book: Book) = bookDao.update(book)

    suspend fun deleteBook(book: Book) = bookDao.delete(book)

    fun getBookById(id: Long): Flow<Book?> = bookDao.getById(id)

    fun getBooksInCollectionlist(): Flow<List<Book>> = bookDao.getBooksInCollectionlist()

    fun getBooksInPlanToReadlist(): Flow<List<Book>> = bookDao.getBooksInPlanToReadlist()

    fun getBooksInReadlist(): Flow<List<Book>> = bookDao.getBooksInReadlist()
}
