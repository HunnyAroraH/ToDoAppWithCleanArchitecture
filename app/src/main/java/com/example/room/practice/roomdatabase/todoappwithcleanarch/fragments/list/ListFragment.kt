package com.example.room.practice.roomdatabase.todoappwithcleanarch.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.room.practice.roomdatabase.todoappwithcleanarch.R
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.models.TodoData
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel.SharedViewModel
import com.example.room.practice.roomdatabase.todoappwithcleanarch.data.viewmodel.TodoViewModel
import com.example.room.practice.roomdatabase.todoappwithcleanarch.databinding.FragmentListBinding
import com.example.room.practice.roomdatabase.todoappwithcleanarch.fragments.list.adapter.ListAdapter
import com.example.room.practice.roomdatabase.todoappwithcleanarch.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(R.layout.fragment_list), SearchView.OnQueryTextListener {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        binding!!.lifecycleOwner = this
        binding!!.mSharedViewModel = mSharedViewModel
        // Setup Recycler View
        setupRecyclerView()


        mTodoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        setHasOptionsMenu(true)
        hideKeyboard(requireActivity())
    }

    private fun setupRecyclerView() {
        val recyclerView = binding?.listRecyclerView
        recyclerView.also {
            it?.adapter = adapter
            it?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            it?.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300

            }
        }
        swipeToDelete(recyclerView!!)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                mTodoViewModel.deleteItem(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeletedData(viewHolder.itemView, itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: TodoData) {
        val snackBar = Snackbar.make(
            view,
            "Deleted ${deletedItem.title}",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mTodoViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> mTodoViewModel.sortByHighPriority.observe(this, {
                adapter.setData(it)
            })
            R.id.menu_priority_low -> mTodoViewModel.sortByLowPriority.observe(this, {
                adapter.setData(it)
            })
        }
        return super.onOptionsItemSelected(item)
    }

    // Show Alert Dialog To Confirm Our All Item Removal
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteAll()
            Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete All?")
        builder.create().show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughQuery(query)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughQuery(query)
        }
        return true
    }


    private fun searchThroughQuery(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"
        mTodoViewModel.searchDatabase(searchQuery).observe(this, {
            it?.let {
                adapter.setData(it)
            }
        })
    }


}