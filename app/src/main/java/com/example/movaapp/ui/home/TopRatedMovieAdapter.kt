package com.example.movaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.TopratedmoviesItemBinding
import com.example.movaapp.model.Result

class TopRatedMovieAdapter :
    ListAdapter<Result, TopRatedMovieAdapter.TopMovieViewHolder>(TopRatedMoviesDiffCallBack()) {

    lateinit var onClick: (Int) -> Unit

    inner class TopMovieViewHolder(val binding: TopratedmoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val view =
            TopratedmoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.topRatedMovieItem = item
        holder.binding.topRatedMovieImg.setOnClickListener {
            onClick(item.id)
        }
    }

    class TopRatedMoviesDiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}