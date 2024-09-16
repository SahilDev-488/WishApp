package com.example.wishapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailScreen(
    id:Long,
    viewModel: WishViewModel,
    navController: NavController
){

    var scnakmassage by remember {
        mutableStateOf("")
    }
    
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.titleState = wish.value.title
        viewModel.description = wish.value.description
    }else{
        viewModel.titleState = ""
        viewModel.description = ""
    }



    Scaffold(
        scaffoldState = scaffoldState
        ,topBar = { AppBar(title =
        if (id != 0L) stringResource(id = R.string.update_list)
        else stringResource(id = R.string.add_list)
        ) {
            navController.navigateUp()
        }
        },


    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(value = viewModel.titleState,
                lable = "Title"
            ) { viewModel.updateTitle(it) }
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(value = viewModel.description,
                lable = "description"
            ) { viewModel.updatedescription(it) }
            Spacer(modifier = Modifier.height(10.dp))
            ElevatedButton(onClick = {
                                     if(viewModel.titleState.isNotEmpty() &&
                                         viewModel.description.isNotEmpty()){
                                         if(id != 0L){
                                             viewModel.updateWish(
                                                 Wish(
                                                     id = id,
                                                     title = viewModel.titleState.trim(),
                                                     description = viewModel.description.trim()
                                                 )
                                             )

                                         }else{
                                              //Add Wish
                                             viewModel.addWish(
                                                 wish = Wish(
                                                     title = viewModel.titleState.trim(),
                                                     description = viewModel.description.trim()
                                                 )
                                             )
                                             scnakmassage = "Wish has been Created"
                                         }

                                     }else{
                                         scnakmassage = "Enter Fields is Required to Create a Wish"

                                     }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(scnakmassage)
                    navController.navigateUp()
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(id = R.color.AppBar))
                ) {
                Text(text =
                if(id != 0L) stringResource(id = R.string.update_list)
                    else stringResource(id = R.string.add_list),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }

        }
    }
}
@Composable
fun WishTextField(
    value:String,
    lable:String,
    onValueChange:(String) -> Unit
){
    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        label = { Text(text = lable, color = Color.Black)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedLabelColor = colorResource(id = R.color.AppBar),
            unfocusedLabelColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.AppBar),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black)
        )
        )

}