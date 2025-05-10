package com.example.ynion.repository

import android.util.Log
import com.example.ynion.ApiService
import com.example.ynion.models.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteRepository {
    private val BASE_URL = "https://tristrac-api.yeems214.xyz/public/"
    private val TAG: String = "CHECK_RESPONSE"

    fun getAllNotes(){
        val api = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        api.getAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        for (Note in it){
                            Log.i(TAG,"onResponse: NoteID=${Note.NoteID}, OwnerID=${Note.OwnerID}, MediaID = ${Note.MediaID}, Title=${Note.Title}, Content=${Note.Content}, createAt=${Note.createAt}, updatedAt=${Note.updateAt}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {

            }


        })
    }//end of GetAllNotes

    fun getNoteByID(targetNoteID: Int) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful) {
                    val noteList = response.body()
                    val note = noteList?.find { it.NoteID == targetNoteID }

                    if (note != null) {
                        Log.i(TAG, "Note Found: NoteID=${note.NoteID}, OwnerID=${note.OwnerID}, MediaID=${note.MediaID}, Title=${note.Title}, Content=${note.Content}, createAt=${note.createAt}, updatedAt=${note.updateAt}")
                    } else {
                        Log.e(TAG, "Note with ID $targetNoteID not found")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }//end of getNoteByID

}