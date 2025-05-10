package com.example.ynion.models

data class Collaboration(
    val CollaborationID:Int,
    val NoteID:Int,
    val UserID:Int,
    val Permission:String,
    val createdAt:String,
    val updatedAt:String
)
