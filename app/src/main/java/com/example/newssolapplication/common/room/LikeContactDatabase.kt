package com.example.newssolapplication.common.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LikeContact::class], version = 1)
abstract class LikeContactDatabase : RoomDatabase(){

    abstract fun likeContactDao(): LikeContactDao

    companion object{
        private var INSTANCE: LikeContactDatabase? = null

        fun getInstance(context: Context): LikeContactDatabase?{
            if(INSTANCE == null){
                synchronized(LikeContactDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LikeContactDatabase::class.java, "likeContact")
                        .fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}