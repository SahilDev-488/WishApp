package com.example.wishapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wishapp.data.Wish


@Composable
fun Navigation(
    viewmodel: WishViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController,
        startDestination = Screen.FirstScreen.route ) {
        composable(Screen.FirstScreen.route){
            HomeView(navController = navController,
                viewmodel
                )
        }
        composable(Screen.SecondScreen.route+"/{id}",
            //basically this concept do retrieving the  id with this arguments consideration that will do for us
            arguments = listOf(
                //checking the arguments
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false 
                }
            )
            ){entery->
            val id = if(entery.arguments != null) entery.arguments!!.getLong("id") else 0L
            AddEditDetailScreen(
                id = id,
                viewModel = viewmodel,
                navController =navController )
        }

    }

}