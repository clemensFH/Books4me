package com.example.books4me

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.books4me.API.BookServiceImpl
import com.example.books4me.navigation.Navigation
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }

//        val bookService = BookServiceImpl()
//
//        val books = runBlocking {
//            bookService.searchBooksByTitle("Harry+Potter+and+the+Chamber+of+Secrets")
//        }
//
//        books.forEach { println(it) }

//        val api = BookAPI()
//
//        val body = runBlocking {
//            println("inside")
//            api.foo()
//        }

//        var body = ""
//        CoroutineScope(Dispatchers.Default).launch {
//            body = api.foo()
//            println("inside")
//        }

//        println("outside")
    }
}