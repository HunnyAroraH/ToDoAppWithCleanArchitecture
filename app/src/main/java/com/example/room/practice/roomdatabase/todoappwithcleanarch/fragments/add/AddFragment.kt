package com.example.room.practice.roomdatabase.todoappwithcleanarch.fragments.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.room.practice.roomdatabase.todoappwithcleanarch.R
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel.SharedViewModel
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment(R.layout.fragment_add) {

    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedVieModel: SharedViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        spinner_priority_add.onItemSelectedListener = mSharedVieModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertData() {
        val mTitle = et_title_add.text.toString()
        val mPriority = spinner_priority_add.selectedItem.toString()
        val mDescription = et_description_title.text.toString()
        val validation = mSharedVieModel.verifyData(mTitle, mDescription)
        if (validation) {
            val newData =
                TodoData(0, mTitle, mSharedVieModel.parsePriority(mPriority), mDescription)
            mTodoViewModel.insertData(newData)
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please Fill out fields", Toast.LENGTH_LONG).show()
        }
    }


}