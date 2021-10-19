package com.example.newssolapplication.common.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "likeContact")
data class LikeContact(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name="first_music")
    var firstMusic: String,

    @ColumnInfo(name = "second_music")
    var secondMusic: String,

    @ColumnInfo(name = "third_music")
    var thirdMusic : String
){ constructor() : this(null,"","","","")}
