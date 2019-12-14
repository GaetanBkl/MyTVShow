package com.example.tvshow

import android.util.Log
import com.example.tvshow.model.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TVShowRepository {
    private val api: API_TVShow

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API_TVShow::class.java)
    }

    fun getPopularTVShow(page: Int = 1) {
        api.getPopularTVShow(page = page)
            .enqueue(object : Callback<TVShowResponse> {
                override fun onResponse(
                    call: Call<TVShowResponse>,
                    response: Response<TVShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "TVShow: ${responseBody.tv}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }
}