package com.example.books4me.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.books4me.screens.CollectionScreenContent
import com.example.books4me.screens.LandingPageScreen
import com.example.books4me.screens.PlanToReadScreenContent
import com.example.books4me.screens.ReadlistScreenContent

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Readlist : Screen("readlist", "Readlist", Icons.Default.List)
    object PlanToRead : Screen("plan_to_read", "Plan-to-Read", Icons.Default.ArrowForward)
    object Collection : Screen("collection", "Collection", Icons.Default.Done)
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    /*Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->*/
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            //modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { LandingPageScreen() }
            composable(Screen.Readlist.route) { ReadlistScreenContent() }
            composable(Screen.PlanToRead.route) { PlanToReadScreenContent() }
            composable(Screen.Collection.route) { CollectionScreenContent() }
        }
    }


/*@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Readlist,
        Screen.PlanToRead,
        Screen.Collection
    )
    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}*/

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
