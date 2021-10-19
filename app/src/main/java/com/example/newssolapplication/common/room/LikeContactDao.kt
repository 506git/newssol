package com.example.newssolapplication.common.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LikeContactDao {
    @Query("SELECT * FROM likeContact ORDER BY id ASC")
    fun getLikeList() : LiveData<List<LikeContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(likeContact: LikeContact)

    @Delete
    fun delete(likeContact: LikeContact)

    @Query("SELECT title FROM likeContact")
    fun selectTitle() : List<String>
}