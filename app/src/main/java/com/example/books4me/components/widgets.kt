package com.example.books4me.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.books4me.API.dto.Book
import com.example.books4me.navigation.Screen

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Readlist") },
            selected = currentRoute == Screen.Readlist.route,
            onClick = { navController.navigate(Screen.Readlist.route) },
            label = { Text("Readlist") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ArrowForward, contentDescription = "Plan-to-Read") },
            selected = currentRoute == Screen.PlanToRead.route,
            onClick = { navController.navigate(Screen.PlanToRead.route) },
            label = { Text("Plan-to-Read") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Done, contentDescription = "Collection") },
            selected = currentRoute == Screen.Collection.route,
            onClick = { navController.navigate(Screen.Collection.route) },
            label = { Text("Collection") }
        )
    }
}