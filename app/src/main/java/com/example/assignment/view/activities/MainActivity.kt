package com.example.assignment.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import assignment.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.example.assignment.model.remote.ApiService
import com.example.assignment.utils.UrlModify
import com.example.assignment.view.adapter.MovieAdapter
import com.example.assignment.viewmodel.createFactory
import com.example.assignment.viewmodel.list.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv.layoutManager = GridLayoutManager(this, 2)
        initViewModel()
        initCall()
        loadMovies()
    }

    private fun loadMovies() {
        viewModel.getAllMovies()
        viewModel.resultAllMovie.observe(this) {
            adapter = MovieAdapter(this, it.data)
            binding.rv.adapter = adapter
        }
    }

    private fun loadSingleMovie() {
        binding.apply {
            linear.visibility = View.VISIBLE
            rv.visibility = View.GONE
            viewModel.getMovie(binding.editTextSearchMovie.text.toString().toInt())
            viewModel.result.observe(this@MainActivity) {
                textViewMovieName.text = it.name
                textViewShows.text = it.tvShows.toString()
                Glide.with(baseContext).load(UrlModify.modify(it.imageUrl)).into(ivMovie)
            }
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

        binding.apply {
            btn.setOnClickListener {
                loadSingleMovie()
                imageViewBack.visibility = View.VISIBLE
            }

            imageViewBack.setOnClickListener {
                rv.visibility = View.VISIBLE
                linear.visibility = View.GONE
                imageViewBack.visibility = View.GONE
                editTextSearchMovie.text?.clear()
            }
        }
    }

    private fun initViewModel() {
        val apiService = ApiService.getInstance()
        val factory = MainViewModel(apiService).createFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
}