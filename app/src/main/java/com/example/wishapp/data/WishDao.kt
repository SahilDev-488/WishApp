package com.example.wishapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao{

    //OnConflictStrategy.IGNORE this can do if wish is already created the it will ignore the thing and created the item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
      abstract suspend fun addWish(wishEntity:Wish)

      //load all the data from wish table (* mean All) Flow return ascny the list of wish. Flow is like a live data that handle the data manipulation
      @Query("select * from `wish-table`")
      abstract fun getAllWish():Flow<List<Wish>>

      @Update
      abstract suspend fun updateWish(wishEntity: Wish)

      @Delete
      abstract suspend fun deleteWish(wishEntity: Wish)

      //it is load all the wish but filter only it's id
      @Query("select * from `wish-table` where(id=:id)")
      abstract fun getWishId(id:Long):Flow<Wish>



}