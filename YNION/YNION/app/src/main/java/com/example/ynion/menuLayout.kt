package com.example.ynion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [menuLayout.newInstance] factory method to
 * create an instance of this fragment.
 */
class menuLayout : Fragment() {

    private lateinit var linearList: LinearLayout
    private var menulayout: MenuFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_layout, container, false)

        val accountInformation = view.findViewById<LinearLayout>(R.id.accountInformation)

        accountInformation.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.menuLayout, AccountInformation())
                .commit()
        }


        return view

        val logoutTextView: ConstraintLayout = view.findViewById(R.id.logoutOption)
        logoutTextView.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout() { // Clear user data, such as shared preferences or session tokens
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        sharedPreferences?.edit()?.clear()?.apply()
        // Redirect to login or another appropriate activity
        val intent = Intent(activity, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        // Optionally, show a message
        Toast.makeText(activity, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment menuLayout.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            menuLayout().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}