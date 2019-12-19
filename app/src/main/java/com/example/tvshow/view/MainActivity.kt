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
    private lateinit var popularTVShowLayoutManager: LinearLayoutManager

    private var popularTVShowPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularTVShow = findViewById(R.id.popular_tvshow)
        popularTVShowLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularTVShow.layoutManager = popularTVShowLayoutManager
        popularTVShowAdapter = TVShowAdapter(mutableListOf())
        popularTVShow.adapter = popularTVShowAdapter

        getPopularTVShow()
    }

    private fun getPopularTVShow() {
        TVShowRepository.getPopularTVShow(
            popularTVShowPage,
            onError = ::onError,
            onSuccess = ::onPopularTVShowFetched
        )
    }

    private fun onPopularTVShowFetched(tvshow: List<TVShow>) {
        popularTVShowAdapter.appendTVShow(tvshow)
        attachPopularTVShowOnScrollListener()
    }

    private fun attachPopularTVShowOnScrollListener() {
        popularTVShow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularTVShowLayoutManager.itemCount
                val visibleItemCount = popularTVShowLayoutManager.childCount
                val firstVisibleItem = popularTVShowLayoutManager.findFirstVisibleItemPosition()

                if(firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularTVShow.removeOnScrollListener(this)
                    popularTVShowPage++
                    getPopularTVShow()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_tv), Toast.LENGTH_SHORT).show()
    }
}
