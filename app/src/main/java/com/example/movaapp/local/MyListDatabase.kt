package com.example.movaapp.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyListItem::class], version = 1)
abstract class MyListDatabase:RoomDatabase() {

    abstract fun getDao():MyListDao
}