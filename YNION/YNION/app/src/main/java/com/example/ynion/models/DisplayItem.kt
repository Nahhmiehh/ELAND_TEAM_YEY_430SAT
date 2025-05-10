package com.example.ynion.models

sealed class DisplayItem {
    data class Log(val logEntry: ActivityLogEntry) : DisplayItem()
    data class Header(val title: String, val view: Int) : DisplayItem()
}