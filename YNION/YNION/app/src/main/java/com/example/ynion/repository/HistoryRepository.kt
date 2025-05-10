package com.example.ynion.repository

import android.util.Log
import com.example.ynion.ApiService
import com.example.ynion.models.History
import com.example.ynion.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryRepository {
    private val BASE_URL = "https://tristrac-api.yeems214.xyz/public/"
    private val TAG: String = "CHECK_RESPONSE"

    fun getAllHistory(){
        val api = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)

        api.getAllHistory().enqueue(object : Callback<List<History>>{
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        for (History in it){
                            Log.i(TAG,"onResponse: HistoryID = ${History.HistoryID}, NoteID=${History.NoteID}, UserID=${History.UserID}, contentSnap=${History.contentSnap}, dateEdited=${History.dateEdited}, editBy=${History.editedBy}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }
        })
    }//end of getAllHistory

    fun getHistoryByID(targetID: Int) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllHistory().enqueue(object : Callback<List<History>> {
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>) {
                if (response.isSuccessful) {
                    val historyList = response.body()
                    val history = historyList?.find { it.HistoryID == targetID }
                    if (history != null) {
                        Log.i(TAG, "History Found: ID=${history.HistoryID}, NoteID=${history.NoteID}, UserID=${history.UserID}, contentSnap=${history.contentSnap}, dateEdited=${history.dateEdited}, editBy=${history.editedBy}")
                    } else {
                        Log.e(TAG, "History with ID=$targetID not found")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }




}