package com.example.books4me.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.books4me.components.BookDetailView
import com.example.books4me.screens.*
import com.example.books4me.viewmodels.*
import com.example.books4me.worker.BookDatabase
import com.example.books4me.worker.BookRepository

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Readlist : Screen("readlist", "Readlist", Icons.Default.List)
    object PlanToRead : Screen("plan_to_read", "Plan-to-Read", Icons.Default.ArrowForward)
    object Collection : Screen("collection", "Collection", Icons.Default.Done)
    object BookDetail : Screen("book_detail/{bookId}", "Book Detail", Icons.Default.List) {
        fun createRoute(bookId: Long) = "book_detail/$bookId"
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val db = BookDatabase.getDatabase(LocalContext.current)
    val repository = BookRepository(bookDao = db.bookDao())
    val factory = BookViewModelFactory(repository = repository)
    val readlistViewModel: ReadlistViewModel = viewModel(factory = factory)
    val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = factory)
    val planToReadlistViewModel: PlanToReadlistViewModel = viewModel(factory = factory)
    val collectionListViewModel: CollectionListViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Home.route) { HomeScreen(navController, homeScreenViewModel) }
        composable(route = Screen.Readlist.route) { ReadlistScreen(navController, readlistViewModel) }
        composable(route = Screen.PlanToRead.route) { PlanToReadScreen(navController, planToReadlistViewModel) }
        composable(route = Screen.Collection.route) { CollectionScreen(navController, collectionListViewModel) }
        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: throw IllegalStateException("Book ID is required")
            BookDetailView(navController, bookId, homeScreenViewModel)
        }
    }
}
