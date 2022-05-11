package com.example.assignment.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import assignment.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import com.example.assignment.model.remote.ApiService
import com.example.assignment.utils.UrlModify
import com.example.assignment.view.adapter.MovieAdapter.Companion.MOVIE_ID
import com.example.assignment.viewmodel.createFactory
import com.example.assignment.viewmodel.detail.DetailViewModel

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initCall()
    }

    private fun loadMovieDetail() {
        viewModel.getMovie(intent.getStringExtra(MOVIE_ID)?.toInt() ?: 6)
        viewModel.result.observe(this) {
            binding.textViewMovieName.text = it.name
            binding.textViewShows.text = it.tvShows.toString()
            Glide.with(this).load(UrlModify.modify(it.imageUrl)).into(binding.ivMovie)
        }
    }

    private fun initCall() {
        viewModel.liveDataForProgress.observe(this) {
            if (it) {
                binding.loadingSpinner.visibility = View.VISIBLE
            } else {
                binding.loadingSpinner.visibility = View.GONE
            }
        }
    }

    private fun initViewModel() {
        val apiService = ApiService.getInstance()
        val factory = DetailViewModel(apiService).createFactory()
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        loadMovieDetail()
    }
}