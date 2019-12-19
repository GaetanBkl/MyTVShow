package com.example.tvshow.model

import com.google.gson.annotations.SerializedName

data class TVShowResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tv: List<TVShow>,
    @SerializedName("total_pages") val pages: Int
)