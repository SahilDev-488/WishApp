package com.example.wishapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishapp.data.Wish
import com.example.wishapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
):ViewModel(){

    var titleState by mutableStateOf("")
    var description by mutableStateOf("")

    fun updateTitle(newString: String){
        titleState = newString
    }
    fun updatedescription(newString: String){
        description = newString
    }
    //lateinit var it is very powerful in the terms of using it will basically do the they provide the
    //promise to kotlin compiler that they can be initialize but later when the object is created.
    lateinit var getAllWish: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWish = wishRepository.getWish()
        }
    }
    fun addWish(wish: Wish){
        //Dispatchers.IO  is more efficient way to take the input output manageable way
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.addWish(wish = wish)
        }
    }
    fun updateWish(wish: Wish){
        viewModelScope.launch {
            wishRepository.updateWish(wish = wish)
        }
    }
    fun deleteWish(wish: Wish){
        viewModelScope.launch {
            wishRepository.deleteWish(wish = wish)
        }
    }

    fun getWishById(id:Long):Flow<Wish>{
        return wishRepository.getWishById(id)
    }


}
