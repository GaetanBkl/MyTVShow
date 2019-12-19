package com.example.tvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshow.model.TVShow
import com.example.tvshow.model.TVShowAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var popularTVShow: RecyclerView
    private lateinit var popularTVShowAdapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularTVShow = findViewById(R.id.popular_tvshow)
        popularTVShow.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularTVShowAdapter = TVShowAdapter(listOf())
        popularTVShow.adapter = popularTVShowAdapter

        TVShowRepository.getPopularTVShow(
            onError = ::onError,
            onSuccess = ::onPopularTVShowFetched
        )
    }

    private fun onPopularTVShowFetched(tvshow: List<TVShow>) {
        popularTVShowAdapter.updateTVShow(tvshow)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_tv), Toast.LENGTH_SHORT).show()
    }
}
