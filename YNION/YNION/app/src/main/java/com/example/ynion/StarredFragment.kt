package com.example.ynion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.Adapter.NoteAdapterClass
import com.example.ynion.Adapter.StarredAdapterClass
import com.example.ynion.dataclass.StarredDataClass
import com.example.ynion.models.Note


class StarredFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NoteAdapterClass
    private lateinit var starredNotes: ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_starred, container, false)
        recyclerView = view.findViewById(R.id.starredNoteList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initial load
        val allNotes = (activity as? MainActivity)?.getAllNotes() ?: arrayListOf()
        starredNotes = ArrayList(allNotes.filter { it.isStarred })

        notesAdapter = NoteAdapterClass(
            starredNotes,
            onNoteClick = { /* Optional: show detail fragment */ },
            onStarClick = { note ->
                note.isStarred = !note.isStarred
                refreshStarredNotes()
                val message = if (note.isStarred) "Added to Favorites" else "Removed from Favorites"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { note ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes") { _, _ ->
                        val index = starredNotes.indexOf(note)
                        if (index != -1) {
                            starredNotes.removeAt(index)
                            notesAdapter.notifyItemRemoved(index)
                            Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                            refreshStarredNotes()  // Refresh the list to reflect changes
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )

        recyclerView.adapter = notesAdapter
        return view
    }

    override fun onResume() {
        super.onResume()
        refreshStarredNotes()
    }

    private fun refreshStarredNotes() {
        val allNotes = (activity as? MainActivity)?.getAllNotes() ?: arrayListOf()
        starredNotes.clear()
        starredNotes.addAll(allNotes.filter { it.isStarred })
        notesAdapter.notifyDataSetChanged()
    }
}
