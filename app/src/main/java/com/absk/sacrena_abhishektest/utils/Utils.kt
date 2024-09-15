package com.absk.sacrena_abhishektest.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

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

        val Date.toTimeAgo: String
            get() {
                val now = Date()
                val durationMillis = now.time - this.time

                if (durationMillis < 0) {
                    return "In the future" // Edge case if the date is in the future
                }

                val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis)
                val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
                val days = TimeUnit.MILLISECONDS.toDays(durationMillis)

                return when {
                    seconds < 60 -> {
                        if (seconds == 1L) "1 second ago" else "$seconds seconds ago"
                    }

                    minutes < 60 -> {
                        if (minutes == 1L) "1 minute ago" else "$minutes minutes ago"
                    }

                    hours < 24 -> {
                        if (hours == 1L) "1 hour ago" else "$hours hours ago"
                    }

                    days < 7 -> {
                        if (days == 1L) "1 day ago" else "$days days ago"
                    }

                    days < 30 -> {
                        val weeks = days / 7
                        if (weeks == 1L) "1 week ago" else "$weeks weeks ago"
                    }

                    days < 365 -> {
                        val months = days / 30
                        if (months == 1L) "1 month ago" else "$months months ago"
                    }

                    else -> {
                        val years = days / 365
                        if (years == 1L) "1 year ago" else "$years years ago"
                    }
                }
            }
    }
}