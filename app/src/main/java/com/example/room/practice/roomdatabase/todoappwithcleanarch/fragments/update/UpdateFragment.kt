package com.example.room.practice.roomdatabase.todoappwithcleanarch.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.room.practice.roomdatabase.todoappwithcleanarch.R
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel.SharedViewModel
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel.TodoViewModel
import com.example.room.practice.roomdatabase.todoappwithcleanarch.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment(R.layout.fragment_update) {

    private val args: UpdateFragmentArgs by navArgs()
    private val viewModel: SharedViewModel by viewModels()
    private val toDoViewModel: TodoViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdateBinding.bind(view)
        binding.args = args

        setHasOptionsMenu(true)
        binding.spinnerPriorityUpdate.onItemSelectedListener = viewModel.listener

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            updateItem()
        } else if (item.itemId == R.id.menu_delete) {
            confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(context, "Deleted '${args.currentItem.title}'", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'")
        builder.setMessage("Are you sure you want to delete? ${args.currentItem.title}")
        builder.create().show()
    }

    private fun updateItem() {
        val title = et_title_update.text.toString()
        val description = et_description_update.text.toString()
        val priority = spinner_priority_update.selectedItem.toString()
        val validation = viewModel.verifyData(title, description)
        if (validation) {
            val todoData = TodoData(
                args.currentItem.id, title, viewModel.parsePriority(priority), description
            )
            toDoViewModel.updateData(todoData)
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Please Fill Out All fields", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}