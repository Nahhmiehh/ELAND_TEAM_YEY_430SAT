package com.example.ynion.models


import java.io.Serializable

data class Note(
    val NoteID: Int?,
    val OwnerID: Int?,
    val MediaID: Int?,
    var Title: String,
    var Content: String,
    val createAt: String,
    val updateAt: String,
    var isStarred: Boolean = false
) : Serializable

