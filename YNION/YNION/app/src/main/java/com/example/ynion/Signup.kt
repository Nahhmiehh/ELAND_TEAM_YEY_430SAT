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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ynion.models.User
import com.example.ynion.repository.UserRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sign

class Signup : AppCompatActivity() {

    private val TAG = "SignupActivity"

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        usernameEditText = findViewById(R.id.ET_Username)
        emailEditText    = findViewById(R.id.ET_Email)

        val passwordLayout  = findViewById<TextInputLayout>(R.id.ET_Password)
        val confirmLayout   = findViewById<TextInputLayout>(R.id.ET_ConfirmPassword)

        passwordEditText  = (passwordLayout.editText
            ?: throw IllegalStateException("Password field missing")) as TextInputEditText
        confirmPasswordEditText = (confirmLayout.editText
            ?: throw IllegalStateException("Confirm-password field missing")) as TextInputEditText

        findViewById<TextView>(R.id.logInHereTV).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        findViewById<Button>(R.id.SignupBtn).setOnClickListener {
            userRepository.signupUser(
                username = usernameEditText.text.toString().trim(),
                email = emailEditText.text.toString().trim(),
                password = passwordEditText.text.toString(),
                confirmPassword = confirmPasswordEditText.text.toString(),
                onSuccess = { response ->
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                    if (response.message.contains("successfully", ignoreCase = true)) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                },
                onValidationError = { msg ->
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                },
                onError = { msg ->
                    Toast.makeText(this, "Signup failed: $msg", Toast.LENGTH_LONG).show()
                    Log.e(TAG, "Signup error: $msg")
                }
            )
        }
    }
}