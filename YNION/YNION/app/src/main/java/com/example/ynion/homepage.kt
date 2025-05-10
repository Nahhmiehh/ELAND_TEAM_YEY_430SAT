package com.example.ynion

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.Adapter.NoteAdapterClass
import com.example.ynion.dataclass.NoteDataClass
import com.example.ynion.models.Note
import java.util.Date
import java.util.Locale


class homepage : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NoteAdapterClass
    private lateinit var noteList: ArrayList<Note>
    private var listener: OnNoteClickListener? = null
    private lateinit var addNoteBtn: ImageView
    private lateinit var searchField: EditText

    interface OnNoteClickListener {
        fun onNoteSelected(note: Note)
        fun onNoteAdded(note: Note)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNoteClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnNoteClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)
        recyclerView = view.findViewById(R.id.activityList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteList = ArrayList((activity as? MainActivity)?.getAllNotes() ?: listOf())

        notesAdapter = NoteAdapterClass(
            noteList,
            onNoteClick = { note ->
                Toast.makeText(requireContext(), "Created on: ${note.createAt}", Toast.LENGTH_SHORT).show()
                listener?.onNoteSelected(note)
            },
            onStarClick = { note ->
                note.isStarred = !note.isStarred
                notesAdapter.notifyDataSetChanged()
                val message = if (note.isStarred) "Added to Favorites" else "Removed from Favorites"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { note ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes") { _, _ ->
                        val index = noteList.indexOf(note)
                        if (index != -1) {
                            noteList.removeAt(index)
                            notesAdapter.notifyItemRemoved(index)
                            Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )

        recyclerView.adapter = notesAdapter

        addNoteBtn = view.findViewById(R.id.addNoteBtn)
        addNoteBtn.setOnClickListener {
            showAddNoteDialog()
        }

        searchField = view.findViewById(R.id.searchFieldBox)
        searchField.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val query = searchField.text.toString()
                if (query.isNotBlank()) {
                    val filteredNotes = noteList.filter { it.Title?.contains(query, ignoreCase = true) == true }
                    notesAdapter.updateList(ArrayList(filteredNotes))
                } else {
                    notesAdapter.updateList(noteList)
                }
                true
            } else {
                false
            }
        }

        return view
    }

    private fun showAddNoteDialog() {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val titleInput = EditText(requireContext()).apply {
            hint = "Note Title"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        layout.addView(titleInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Note")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val title = titleInput.text.toString()

                if (title.isNotBlank()) {
                    val currentDateTime = getCurrentDateTime()
                    val newNote = Note(
                        NoteID = null,
                        OwnerID = null,
                        MediaID = null,
                        Title = title,
                        Content = "",
                        createAt = currentDateTime,
                        updateAt = "",
                        isStarred = false
                    )
                    noteList.add(newNote)
                    notesAdapter.notifyItemInserted(noteList.size - 1)
                    listener?.onNoteAdded(newNote)
                    Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Please fill in the note title", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun getNotes(): List<Note> = noteList

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}