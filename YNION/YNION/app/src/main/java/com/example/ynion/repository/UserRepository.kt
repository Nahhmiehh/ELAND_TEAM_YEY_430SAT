package com.example.ynion.repository

import android.util.Log
import com.example.ynion.ApiService
import com.example.ynion.SignupResponse
import com.example.ynion.UserSignupRequest
import com.example.ynion.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class UserRepository {
    private val BASE_URL = "https://tristrac-api.yeems214.xyz/public/"
    private val TAG: String = "CHECK_RESPONSE"

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun loginUser(
        username: String,
        password: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    val user = users?.find { it.Username.equals(username, ignoreCase = true) }

                    when {
                        user == null -> onError("Username not found")
                        user.Password != password -> onError("Incorrect password")
                        else -> onSuccess(user)
                    }
                } else {
                    onError("Server error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "Login failed: ${t.message}")
                onError("Login failed: ${t.message}")
            }
        })
    }//end of loginUser


    fun signupUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: (SignupResponse) -> Unit,
        onValidationError: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        // Validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            onValidationError("Please fill in all fields")
            return
        }
        if (password != confirmPassword) {
            onValidationError("Passwords do not match")
            return
        }

        val request = UserSignupRequest(
            username = username,
            email = email,
            password = password,
            profilePicture = "default.jpg"
        )

        api.createNewUser(request).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                        ?: onError("Empty response body")
                } else {
                    val err = response.errorBody()?.string() ?: "Unknown error"
                    onError(err)
                    Log.e(TAG, "API error: $err")
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                val msg = t.message ?: "Network failure"
                onError(msg)
                Log.e(TAG, "Network failure: $msg", t)
            }
        })
    }//end of signupUser




    fun getAllUsers(){
        val api = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        for (User in it){
                            Log.i(TAG,"onResponse: UserID=${User.UserID}, Username=${User.Username}, Email=${User.Email}, Password=${User.Password}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }//end of GetAllUsers

    fun getUserByID(targetID: Int) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    val user = userList?.find { it.UserID == targetID }
                    if (user != null) {
                        Log.i(TAG, "User Found: ID=${user.UserID}, Name=${user.Username}, Email=${user.Email}, Password=${user.Password}")
                    } else {
                        Log.e(TAG, "User with ID=$targetID not found")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }//end of getUserByID

    fun getUserByUsername(targetUsername: String) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    val user = userList?.find { it.Username.equals(targetUsername, ignoreCase = true) }
                    if (user != null) {
                        Log.i(TAG, "User Found: UserID=${user.UserID} Username=${user.Username}, Email=${user.Email}, Password=${user.Password}")
                    } else {
                        Log.e(TAG, "User with username '$targetUsername' not found")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }//end of getUserByUsername

    fun getUserByEmail(targetEmail: String) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    val user = userList?.find { it.Email.equals(targetEmail, ignoreCase = true) }
                    if (user != null) {
                        Log.i(TAG, "User Found: UserID=${user.UserID}, Email=${user.Email}, Username=${user.Username}, Password=${user.Password}")
                    } else {
                        Log.e(TAG, "User with email '$targetEmail' not found")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }//end of getUserByEmail


}//end of class