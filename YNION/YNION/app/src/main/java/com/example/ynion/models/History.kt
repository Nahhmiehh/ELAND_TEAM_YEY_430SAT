package com.example.ynion.models

data class History(
    val HistoryID:Int,
    val NoteID:Int,
    val UserID:Int,
    val contentSnap: String,
    val dateEdited: String,
    val editedBy: Int
)
