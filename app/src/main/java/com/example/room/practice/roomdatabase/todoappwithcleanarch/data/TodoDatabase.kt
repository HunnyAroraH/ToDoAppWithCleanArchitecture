package com.example.room.practice.roomdatabase.todoappwithcleanarch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData

@Database(entities = [TodoData::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun toDoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TodoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}