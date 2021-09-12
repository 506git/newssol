package com.example.newssolapplication.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val userName: String? = null,
    val uid: String? = null,
    val userProfile: String? = null
)
