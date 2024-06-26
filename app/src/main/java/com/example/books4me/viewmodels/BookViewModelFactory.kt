package com.example.books4me.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.books4me.worker.BookRepository

class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ReadlistViewModel::class.java) -> ReadlistViewModel(repository) as T
            modelClass.isAssignableFrom(HomeScreenViewModel::class.java) -> HomeScreenViewModel(repository) as T
            modelClass.isAssignableFrom(PlanToReadlistViewModel::class.java) -> PlanToReadlistViewModel(repository) as T
            modelClass.isAssignableFrom(CollectionListViewModel::class.java) -> CollectionListViewModel(repository) as T
//            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
//                val movieId = "DEFAULT VALUE"
//                DetailViewModel(repository, movieId) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}