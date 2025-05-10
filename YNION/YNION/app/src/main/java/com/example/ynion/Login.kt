package com.example.ynion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ynion.models.User
import com.example.ynion.repository.HistoryRepository
import com.example.ynion.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    private val userRepository = UserRepository()
    private val historyRepository = HistoryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signup = findViewById<TextView>(R.id.signUpHereTV)
        val login = findViewById<Button>(R.id.LoginBtn)
        val username = findViewById<EditText>(R.id.ET_Username)
        val password = findViewById<EditText>(R.id.passwordEditText)

        signup.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }

        login.setOnClickListener {
            val enteredUsername = username.text.toString().trim()
            val enteredPassword = password.text.toString().trim()

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userRepository.loginUser(
                username = enteredUsername,
                password = enteredPassword,
                onSuccess = { user ->
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("USER_ID", user.UserID)
                    startActivity(intent)
                    finish()
                },
                onError = { message ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}