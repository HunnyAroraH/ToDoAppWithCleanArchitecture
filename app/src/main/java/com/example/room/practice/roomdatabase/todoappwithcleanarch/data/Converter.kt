package com.example.room.practice.roomdatabase.todoappwithcleanarch.data

import androidx.room.TypeConverter
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}