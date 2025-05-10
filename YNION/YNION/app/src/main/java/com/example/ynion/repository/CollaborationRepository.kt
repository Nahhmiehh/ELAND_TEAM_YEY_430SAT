package com.example.ynion.repository

import android.util.Log
import com.example.ynion.ApiService
import com.example.ynion.models.Collaboration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollaborationRepository {

    private val BASE_URL = "https://tristrac-api.yeems214.xyz/public/"
    private val TAG: String = "CHECK_RESPONSE"


    fun getAllCollaborations(){
        val api = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        api.getAllCollaborations().enqueue(object : Callback<List<Collaboration>> {
            override fun onResponse(
                call: Call<List<Collaboration>>,
                response: Response<List<Collaboration>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        for (Collaboration in it){
                            Log.i(TAG,"onResponse: CollaborationID=${Collaboration.CollaborationID}, NoteID=${Collaboration.NoteID}, UserID=${Collaboration.UserID}, Permission = ${Collaboration.Permission}, createAt=${Collaboration.createdAt}, updatedAt=${Collaboration.updatedAt}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Collaboration>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }


        })
    }//end of GetAllCollaborations

    fun getCollaborationByID(targetID: Int) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllCollaborations().enqueue(object : Callback<List<Collaboration>> {
            override fun onResponse(
                call: Call<List<Collaboration>>,
                response: Response<List<Collaboration>>
            ) {
                if (response.isSuccessful) {
                    val collaborations = response.body()
                    val collaboration = collaborations?.find { it.CollaborationID == targetID }

                    if (collaboration != null) {
                        Log.i(TAG, "Found: CollaborationID=${collaboration.CollaborationID}, NoteID=${collaboration.NoteID}, UserID=${collaboration.UserID}, Permission=${collaboration.Permission}, createdAt=${collaboration.createdAt}, updatedAt=${collaboration.updatedAt}")
                    } else {
                        Log.e(TAG, "No collaboration found with ID $targetID")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Collaboration>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }//end of getCollaborationByID




}