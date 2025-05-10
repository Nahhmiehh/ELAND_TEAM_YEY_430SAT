package com.example.ynion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        // Do the fragment transaction after view is created
        val newFragment = menuLayout() // make sure this is your actual fragment class

        childFragmentManager.beginTransaction()
            .replace(R.id.menuDisplay, newFragment) // menuDisplay should exist inside fragment_menu.xml
            .commit()

        return view
    }
}
