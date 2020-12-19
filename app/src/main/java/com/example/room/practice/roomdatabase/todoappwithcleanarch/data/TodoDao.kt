package com.example.room.practice.roomdatabase.todoappwithcleanarch.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC ")
    fun getAllData(): LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(todoData: TodoData)

    @Update()
    suspend fun updateData(todoData: TodoData)

    @Delete
    suspend fun deleteItem(todoData: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery ")
    fun searchDatabase(searchQuery: String): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<TodoData>>


}