package com.example.ynion.repository

import android.util.Log
import com.example.ynion.ApiService
import com.example.ynion.models.Media
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MediaRepository {
    private val BASE_URL = "https://tristrac-api.yeems214.xyz/public/"
    private val TAG: String = "CHECK_RESPONSE"

    fun getAllMedia(){
        val api = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

        api.getAllMedia().enqueue(object : Callback<List<Media>> {
            override fun onResponse(call: Call<List<Media>>, response: Response<List<Media>>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        for (Media in it){
                            Log.i(TAG,"onResponse: MediaID = ${Media.MediaID}, UserID=${Media.UserID}, mediaName=${Media.mediaName}, mediaType=${Media.mediaType}, dateUploaded=${Media.dateUploaded}, Attributes=${Media.Attributes}, PositionXY=${Media.PositionXY}")
                        }
                    }
                }

            }

            override fun onFailure(call: Call<List<Media>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")

            }


        })
    }//end of GetAllMedia

    fun getMediaByID(targetMediaID: Int) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        api.getAllMedia().enqueue(object : Callback<List<Media>> {
            override fun onResponse(call: Call<List<Media>>, response: Response<List<Media>>) {
                if (response.isSuccessful) {
                    val mediaList = response.body()
                    val media = mediaList?.find { it.MediaID == targetMediaID }

                    if (media != null) {
                        Log.i(TAG, "Media Found: ID=${media.MediaID}, Name=${media.mediaName}, Type=${media.mediaType}, UserID=${media.UserID}, DateUploaded=${media.dateUploaded}, Attributes=${media.Attributes}, PositionXY=${media.PositionXY}")
                    } else {
                        Log.e(TAG, "Media with ID $targetMediaID not found")
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Media>>, t: Throwable) {
                Log.e(TAG, "API Call failed: ${t.message}")
            }
        })
    }//end of GetMediaByID



}