package com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.TodoDao
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.TodoDatabase
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao: TodoDao = TodoDatabase.getDatabase(application).toDoDao()
    private val repository: TodoRepository
    val getAllData: LiveData<List<TodoData>>

    val sortByHighPriority: LiveData<List<TodoData>>
    val sortByLowPriority: LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(todoData)
        }
    }


    fun updateData(todoData: TodoData) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateData(todoData)
    }

    fun deleteItem(todoData: TodoData) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteItem(todoData)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun searchDatabase(query: String): LiveData<List<TodoData>> {
        return repository.searchQuery(query)
    }

}