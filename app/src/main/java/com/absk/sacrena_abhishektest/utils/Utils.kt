package com.absk.sacrena_abhishektest.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
    companion object {
        fun formatToTimeString(date: Date?): String {
            if (date == null) return ""
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return formatter.format(date)
        }

         fun getInitials(name: String): String {
            return name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString("")
        }
    }
}