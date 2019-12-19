package com.example.tvshow.model

import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String
)