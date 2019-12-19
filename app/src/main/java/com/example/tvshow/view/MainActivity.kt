package com.example.tvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tvshow.model.TVShow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TVShowRepository.getPopularTVShow(
            onError = ::onError,
            onSuccess = ::onPopularTCShowFetched
        )
    }

    private fun onPopularTCShowFetched(tv: List<TVShow>) {
        Log.d("MainActivity", "TVShow: $tv")
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_tv), Toast.LENGTH_SHORT).show()
    }
}
