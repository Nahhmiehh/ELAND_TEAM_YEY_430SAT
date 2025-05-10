package com.example.ynion.models

import java.util.Date

data class ActivityLogEntry(

    val primaryText: String, // e.g., "NOTE TITLE" or the main action
    val secondaryText: String, // e.g., "Kent Carl Banzon" or additional details
    val timestamp: Date,
    val userIconResId: Int? = null, // Drawable resource ID for the user/action icon

)

//    val relativeTime: String, // e.g., "Last edited 2 hours ago" - this would be pre-formatted
//    val originalAction: String = "" // Optional: to store the raw action like "edited", "created"
//val id: String,