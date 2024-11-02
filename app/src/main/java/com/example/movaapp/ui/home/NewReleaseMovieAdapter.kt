package com.example.movaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.NewreleasemoviesItemBinding
import com.example.movaapp.model.Result

class NewReleaseMovieAdapter :
    ListAdapter<Result, NewReleaseMovieAdapter.NewReleaseMovieViewHolder>(NewReleaseDiffCallBack()) {

    lateinit var onClick: (Int) -> Unit

    inner class NewReleaseMovieViewHolder(val binding: NewreleasemoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewReleaseMovieViewHolder {
        val view =
            NewreleasemoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewReleaseMovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: NewReleaseMovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.newReleaseItem = item
        holder.binding.newReleaseMovieImg.setOnClickListener {
            onClick(item.id)
        }
    }

    class NewReleaseDiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

}