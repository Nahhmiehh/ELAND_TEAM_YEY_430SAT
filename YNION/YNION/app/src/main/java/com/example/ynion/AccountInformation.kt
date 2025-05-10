package com.example.ynion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class AccountInformation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_account_information, container, false)

        // Reference your back button from the view
        val backBtn = view.findViewById<LinearLayout>(R.id.backBtn2)

        // Set click listener
        backBtn.setOnClickListener {
            // Go back to the default fragment (you can change this to your actual default one)
            parentFragmentManager.beginTransaction()
                .replace(R.id.menuDisplay, menuLayout()) // Replace with your default fragment
                .commit()
        }

        return view
    }
}
