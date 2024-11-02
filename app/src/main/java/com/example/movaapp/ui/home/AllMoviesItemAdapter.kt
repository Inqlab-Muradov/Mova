package com.example.movaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.AllmoviesItemBinding
import com.example.movaapp.model.Result

class AllMoviesItemAdapter :
    ListAdapter<Result, AllMoviesItemAdapter.AllMoviesViewHolder>(AllMoviesDiffCallBack()) {

    lateinit var onClick: (Int) -> Unit

    inner class AllMoviesViewHolder(val binding: AllmoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMoviesViewHolder {
        val view = AllmoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllMoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.allMoviesItem = item
        holder.binding.allMoviesCard.setOnClickListener {
            onClick(item.id)
        }
    }

    class AllMoviesDiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}