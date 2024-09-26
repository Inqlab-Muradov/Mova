package com.example.movaapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMyListItem(myListItem: MyListItem)

    @Query("SELECT*FROM myList_table")
    fun getAllMyListItem(): Flow<List<MyListItem>>

    @Query("SElECT *FROM myList_table WHERE mediaType=:mediaType")
    fun filterMyList(mediaType:String):Flow<List<MyListItem>>

    @Delete
    fun deleteMyListItem(myListItem: MyListItem)

    @Query ("SELECT *FROM myList_table WHERE id = :id")
    fun getMyListById(id:Int) : Flow<MyListItem?>

}