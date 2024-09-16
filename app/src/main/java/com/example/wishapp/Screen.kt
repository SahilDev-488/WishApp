package com.example.wishapp

sealed class Screen (
    val route:String
){
    object FirstScreen : Screen("first-screen")
    object SecondScreen : Screen("second-screen")
}