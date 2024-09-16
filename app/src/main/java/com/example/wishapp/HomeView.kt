package com.example.wishapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.wishapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context = LocalContext.current
    Scaffold(
        topBar = { AppBar(title = "WishList")},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                      navController.navigate(Screen.SecondScreen.route+ "/0L")
            },
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.teal_200)
                ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription =  null)

            }
        }

    ) {
        val wishList = viewModel.getAllWish.collectAsState(initial = listOf())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = colorResource(id = R.color.AppBar))
        ) {
            items(wishList.value, key = {wish-> wish.id }){
                wish ->

                val dismiss = rememberDismissState(
                    confirmStateChange = {
                        if(it==DismissValue.DismissedToEnd ||it == DismissValue.DismissedToStart){
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )

                SwipeToDismiss(state = dismiss,
                    background = {
                                 val color by animateColorAsState(
                                     if(dismiss.dismissDirection == DismissDirection.EndToStart) Color.Blue
                                     else Color.Transparent,
                                     label = ""
                                 )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ){
                            Icon(imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Black
                                )
                        }

                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.45f)}
                    ) {
                    CardView(wish = wish) {
                        val id  = wish.id
                        navController.navigate(Screen.SecondScreen.route +"/$id")
                    }

                }




            }
            }
        
        }
}

@Composable
fun CardView(wish: Wish, onClick:()-> Unit){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clickable { onClick() },
        elevation = 10.dp,
        backgroundColor = Color.White
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
          Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
          Text(text = wish.description)
        }

    }

}