package com.example.books4me.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books4me.screens.*
import com.example.books4me.viewmodels.BookViewModel
import com.example.books4me.viewmodels.BookViewModelFactory
import com.example.books4me.viewmodels.CollectionListViewModel
import com.example.books4me.viewmodels.HomeScreenViewModel
import com.example.books4me.viewmodels.PlanToReadlistViewModel
import com.example.books4me.viewmodels.ReadlistViewModel
import com.example.books4me.worker.BookDatabase
import com.example.books4me.worker.BookRepository

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Readlist : Screen("readlist", "Readlist", Icons.Default.List)
    object PlanToRead : Screen("plan_to_read", "Plan-to-Read", Icons.Default.ArrowForward)
    object Collection : Screen("collection", "Collection", Icons.Default.Done)
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val db = BookDatabase.getDatabase(LocalContext.current)
    val repository = BookRepository(bookDao = db.bookDao())
    val factory = BookViewModelFactory(repository = repository)
    val readlistViewModel: ReadlistViewModel = viewModel(factory = factory)
    val homeScreenViewModel : HomeScreenViewModel = viewModel(factory = factory)
    val planToReadlistViewModel : PlanToReadlistViewModel = viewModel(factory = factory)
    val collectionListViewModel : CollectionListViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, homeScreenViewModel) }
        composable(Screen.Readlist.route) { ReadlistScreen(navController, readlistViewModel) }
        composable(Screen.PlanToRead.route) { PlanToReadScreen(navController, planToReadlistViewModel) }
        composable(Screen.Collection.route) { CollectionScreen(navController, collectionListViewModel) }
    }
}
