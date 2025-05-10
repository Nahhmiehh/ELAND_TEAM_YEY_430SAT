package com.example.ynion

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ynion.models.Note


class NoteDetails : Fragment() {

    private lateinit var noteEditText: EditText
    private lateinit var titleEditText: EditText

    private var currentNote: Note? = null

    private val autoSaveHandler = Handler(Looper.getMainLooper())
    private val autoSaveInterval = 5_000L // 10 seconds

    private val autoSaveRunnable = object : Runnable {
        override fun run() {
            saveNote()
            autoSaveHandler.postDelayed(this, autoSaveInterval)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentNote = arguments?.getSerializable(ARG_NOTE) as? Note
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteEditText = view.findViewById(R.id.noteTextBox)
        titleEditText = view.findViewById(R.id.noteTitlePlaceholder)

        // Populate fields
        titleEditText.setText(currentNote?.Title)
        noteEditText.setText(currentNote?.Content)

        // Start auto-saving every 10 seconds
        autoSaveHandler.postDelayed(autoSaveRunnable, autoSaveInterval)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Stop auto-saving
        autoSaveHandler.removeCallbacks(autoSaveRunnable)
    }

    private fun saveNote() {
        currentNote?.Title = titleEditText.text.toString().trim()
        currentNote?.Content = noteEditText.text.toString().trim()

        Log.d("NoteDetails", "Auto-saving: ${currentNote?.Title} - ${currentNote?.Content}")

        Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_NOTE = "note"

        fun newInstance(note: Note): NoteDetails {
            val fragment = NoteDetails()
            val bundle = Bundle()
            bundle.putSerializable(ARG_NOTE, note)
            fragment.arguments = bundle
            return fragment
        }
    }
}