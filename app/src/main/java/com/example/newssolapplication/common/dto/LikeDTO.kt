package com.example.newssolapplication.common.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LikeDTO(
        var name : String? = "",
        var menuId : String?
)