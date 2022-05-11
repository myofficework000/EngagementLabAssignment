package com.example.assignment.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import assignment.R
import assignment.databinding.ViewHolderMovieBinding
import com.example.assignment.model.response.Data
import com.example.assignment.view.activities.MovieDetailActivity

class MovieAdapter(private val context: Context, private val list: List<Data>) :
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderMovieBinding>(
            layoutInflater,
            R.layout.view_holder_movie, parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = list[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID, movie._id.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }
}

class MovieViewHolder(private val binding: ViewHolderMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(list: Data) {
        binding.data = list
    }
}