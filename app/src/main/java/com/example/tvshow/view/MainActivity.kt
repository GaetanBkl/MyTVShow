package com.example.tvshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthlimchiu.mytvshows.*
import com.example.tvshow.model.TVShow
import com.example.tvshow.model.TVShowAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var popularTVShow: RecyclerView
    private lateinit var popularTVShowAdapter: TVShowAdapter
    private lateinit var popularTVShowLayoutManager: LinearLayoutManager

    private lateinit var topRatedTVShow: RecyclerView
    private lateinit var topRatedTVShowAdapter: TVShowAdapter
    private lateinit var topRatedTVShowLayoutManager: LinearLayoutManager

    private var popularTVShowPage = 1

    private var topRatedTVShowPage = 1


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
        { tvshow -> showTVShowDetails(tvshow) }
        popularTVShow.adapter = popularTVShowAdapter

        topRatedTVShow = findViewById(R.id.top_rated_tvshows)
        topRatedTVShowLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedTVShow.layoutManager = topRatedTVShowLayoutManager
        topRatedTVShowAdapter = TVShowAdapter(mutableListOf())
        { tvshow -> showTVShowDetails(tvshow) }
        topRatedTVShow.adapter = topRatedTVShowAdapter

        getTopRatedTVShow()
        getPopularTVShow()
    }

    private fun getPopularTVShow() {
        TVShowRepository.getPopularTVShow(
            popularTVShowPage,
            onError = ::onError,
            onSuccess = ::onPopularTVShowFetched
        )
    }

    private fun getTopRatedTVShow() {
        TVShowRepository.getTopRatedTVShow(
            topRatedTVShowPage,
            onSuccess = ::onTopRatedTVShowFetched,
            onError = ::onError
        )
    }


    private fun onPopularTVShowFetched(tvshow: List<TVShow>) {
        popularTVShowAdapter.appendTVShow(tvshow)
        attachPopularTVShowOnScrollListener()
    }

    private fun onTopRatedTVShowFetched(tvshow: List<TVShow>) {
        topRatedTVShowAdapter.appendTVShow(tvshow)
        attachTopRatedTVShowOnScrollListener()
    }

    private fun showTVShowDetails(tvshow: TVShow) {
        val intent = Intent(this, TVShowDetailsActivity::class.java)
        intent.putExtra(TVSHOW_BACKDROP, tvshow.backdropPath)
        intent.putExtra(TVSHOW_POSTER, tvshow.posterPath)
        intent.putExtra(TVSHOW_TITLE, tvshow.title)
        intent.putExtra(TVSHOW_RATING, tvshow.rating)
        intent.putExtra(TVSHOW_RELEASE_DATE, tvshow.releaseDate)
        intent.putExtra(TVSHOW_OVERVIEW, tvshow.overview)
        startActivity(intent)
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

    private fun attachTopRatedTVShowOnScrollListener() {
        topRatedTVShow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedTVShowLayoutManager.itemCount
                val visibleItemCount = topRatedTVShowLayoutManager.childCount
                val firstVisibleItem = topRatedTVShowLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedTVShow.removeOnScrollListener(this)
                    topRatedTVShowPage++
                    getTopRatedTVShow()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_tv), Toast.LENGTH_SHORT).show()
    }
}
