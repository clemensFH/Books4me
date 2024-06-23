package com.example.books4me.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books4me.screens.*
import com.example.books4me.viewmodels.BookViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Readlist : Screen("readlist", "Readlist", Icons.Default.List)
    object PlanToRead : Screen("plan_to_read", "Plan-to-Read", Icons.Default.ArrowForward)
    object Collection : Screen("collection", "Collection", Icons.Default.Done)
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val bookViewModel = remember { BookViewModel() }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, bookViewModel) }
        composable(Screen.Readlist.route) { ReadlistScreen(navController, bookViewModel) }
        composable(Screen.PlanToRead.route) { PlanToReadScreen(navController, bookViewModel) }
        composable(Screen.Collection.route) { CollectionScreen(navController, bookViewModel) }
    }
}
