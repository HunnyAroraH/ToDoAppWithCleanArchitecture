package com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.R
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.Priority
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)
    fun checkIfDatabaseEmpty(todoData: List<TodoData>) {
        emptyDatabase.value = todoData.isEmpty()
    }

    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, id: Int, value: Long) {
            when (id) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.purple_700
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            //
        }

    }

    fun verifyData(title: String, description: String): Boolean {
        return !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> (Priority.HIGH)
            "Medium Priority" -> (Priority.MEDIUM)
            "Low Priority" -> (Priority.LOW)
            else -> Priority.LOW
        }
    }

}