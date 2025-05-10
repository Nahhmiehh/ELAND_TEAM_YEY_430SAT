package com.example.ynion.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.dataclass.NoteDataClass
import com.example.ynion.R
import com.example.ynion.models.Note

class NoteAdapterClass(
    private val notes: ArrayList<Note>,
    private val onNoteClick: (Note) -> Unit,
    private val onStarClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapterClass.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitle)
        val starredButton: ImageView = itemView.findViewById(R.id.starredButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notelist, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.Title

        holder.starredButton.setImageResource(
            if (note.isStarred) R.drawable.favorite else R.drawable.unfavorite
        )

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClick(note)  // Trigger delete action
        }

        // Handle item view click (show details, etc.)
        holder.itemView.setOnClickListener {
            onNoteClick(note)
        }

        // Handle starred button click
        holder.starredButton.setOnClickListener {
            onStarClick(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    // Add method to update the list of notes
    fun updateList(newNotes: ArrayList<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}
