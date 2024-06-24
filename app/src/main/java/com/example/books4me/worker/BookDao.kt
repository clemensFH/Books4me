package com.example.books4me.worker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.books4me.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * from books WHERE isInCollectionlist=1")
    fun getBooksInCollectionlist(): Flow<List<Book>>

    @Query("SELECT * from books WHERE isInPlanToReadlist=1")
    fun getBooksInPlanToReadlist(): Flow<List<Book>>

    @Query("SELECT * from books WHERE isInReadlist=1")
    fun getBooksInReadlist(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getById(id: Long): Flow<Book?>
}