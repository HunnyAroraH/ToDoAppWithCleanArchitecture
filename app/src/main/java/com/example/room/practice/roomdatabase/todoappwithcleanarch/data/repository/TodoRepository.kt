package com.example.room.practice.roomdatabase.todoappwithcleanarch.data.repository

import androidx.lifecycle.LiveData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.TodoDao
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData

class TodoRepository(private val todoDao: TodoDao) {
    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()

    var sortByLowPriority: LiveData<List<TodoData>> = todoDao.sortByLowPriority()

    var sortByHighPriority: LiveData<List<TodoData>> = todoDao.sortByHighPriority()

    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }


    suspend fun updateData(todoData: TodoData) {
        todoDao.updateData(todoData)
    }

    suspend fun deleteItem(todoData: TodoData) {
        todoDao.deleteItem(todoData)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    fun searchQuery(searchQuery: String): LiveData<List<TodoData>> {
        return todoDao.searchDatabase(searchQuery)
    }

}