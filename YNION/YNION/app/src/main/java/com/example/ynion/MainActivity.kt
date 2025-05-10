package com.example.ynion

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.models.Note
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), homepage.OnNoteClickListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var homepageFragment: homepage? = null

    // Shared note list
    private val noteList = arrayListOf(
        Note(null, null, null, "Note 1", "", "", "", false),
        Note(null, null, null, "Note 2", "", "", "", false),
        Note(null, null, null, "Note 3", "", "", "", false),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_nav_bar)

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayoutHome)
            if (currentFragment is homepage ||
                currentFragment is StarredFragment ||
                currentFragment is LogsFragment ||
                currentFragment is MenuFragment) {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        if (savedInstanceState == null) {
            homepageFragment = homepage()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayoutHome, homepageFragment!!)
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if (homepageFragment == null) homepageFragment = homepage()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutHome, homepageFragment!!)
                        .commit()
                    true
                }

                R.id.starred -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutHome, StarredFragment())
                        .commit()
                    true
                }

                R.id.logs -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutHome, LogsFragment())
                        .commit()
                    true
                }

                R.id.menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutHome, MenuFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    fun getAllNotes(): List<Note> {
        return noteList
    }

    override fun onNoteSelected(note: Note) {
        val detailsFragment = NoteDetails.newInstance(note)
        bottomNavigationView.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutHome, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNoteAdded(note: Note) {
        noteList.add(note)
    }
}
