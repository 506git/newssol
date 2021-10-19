package com.example.newssolapplication.common.dto

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class CategoryVO(
    var name : String? = "",
    var count : Int? = 0,
    var menuId : String?
)
@IgnoreExtraProperties
data class CategoryIdVO(
    var nature : List<CategoryVO>,
    var rain : List<CategoryVO>,
    var city : List<CategoryVO>,
    var rest : List<CategoryVO>
)