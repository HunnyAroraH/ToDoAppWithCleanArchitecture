package com.example.room.practice.roomdatabase.todoappwithcleanarch.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.databinding.ItemLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    var dataList = emptyList<TodoData>()

    class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoData: TodoData) {
            binding.toDoData = todoData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(todoData: List<TodoData>) {
        val util = ToDoDiffUtil(dataList, todoData)
        val result = DiffUtil.calculateDiff(util)
        this.dataList = todoData
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = dataList.size
}