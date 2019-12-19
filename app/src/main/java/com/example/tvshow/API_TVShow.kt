package com.example.tvshow


import com.example.tvshow.model.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API_TVShow {
    @GET("tv/popular")
    fun getPopularTVShow(
        @Query("api_key") apiKey: String = "56b0da3c7e126ca8f7ad305c07137017",
        @Query("page") page: Int
    ): Call<TVShowResponse>
}
