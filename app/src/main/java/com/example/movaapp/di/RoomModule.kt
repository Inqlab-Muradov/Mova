package com.example.movaapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movaapp.local.MyListDao
import com.example.movaapp.local.MyListDatabase
import com.example.movaapp.local.MyListItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context:Context):MyListDatabase{
        return Room.databaseBuilder(context,MyListDatabase::class.java,"Movadb").build()
    }

    @Provides
    @Singleton
    fun provideMyListDao(myListDatabase: MyListDatabase):MyListDao{
        return myListDatabase.getDao()
    }
}