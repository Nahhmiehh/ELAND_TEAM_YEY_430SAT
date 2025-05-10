package com.example.ynion.models

data class User(
    val UserID:Int? = null,
    val Username:String,
    val Password:String,
    val Email:String,
    val ProfilePicture:String = "Default"
)
