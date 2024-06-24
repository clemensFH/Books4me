package com.example.books4me.worker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.books4me.model.Book

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)

abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    BookDatabase::class.java,
                    "books_db"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}
