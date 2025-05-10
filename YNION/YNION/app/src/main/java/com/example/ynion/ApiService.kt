package com.example.ynion

import com.example.ynion.models.Collaboration
import com.example.ynion.models.History
import com.example.ynion.models.Media
import com.example.ynion.models.Note
import com.example.ynion.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    //Users
    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @POST("users")
    fun createNewUser(@Body user: UserSignupRequest): Call<SignupResponse>

    //Media
    @GET("media")
    fun getAllMedia(): Call<List<Media>>

    //Note
    @GET("note")
    fun getAllNotes(): Call<List<Note>>

    //Collaboration
    @GET("collaboration")
    fun getAllCollaborations(): Call<List<Collaboration>>

    //History
    @GET("history")
    fun getAllHistory(): Call<List<History>>

    @GET("history/{id}")
    fun getHistoryByID(@Path("id") id: Int): Call<History>

}

