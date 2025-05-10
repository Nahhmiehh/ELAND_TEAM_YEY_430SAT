package com.example.ynion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing_page)

        val signIn = findViewById<Button>(R.id.landingpage_Signin)
        val signUp = findViewById<Button>(R.id.landingpage_Signup)

        signIn.setOnClickListener{
            Intent(this@LandingPage, Login::class.java).also { startActivity(it) }
        }

        signUp.setOnClickListener{
            Intent(this@LandingPage, Signup::class.java).also { startActivity(it) }
        }

    }
}