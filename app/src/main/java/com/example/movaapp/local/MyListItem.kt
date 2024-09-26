package com.example.movaapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myList_table")
data class MyListItem(
    @PrimaryKey
    val id:Int,
    val posterPath:String,
    val voteAverage:String,
    val mediaType:String
)
