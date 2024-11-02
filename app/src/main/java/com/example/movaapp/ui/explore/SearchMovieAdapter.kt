package com.example.movaapp.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.SearchmovieItemBinding
import com.example.movaapp.model.Result
import com.example.movaapp.model.ResultX

class SearchMovieAdapter :
    ListAdapter<ResultX, SearchMovieAdapter.SearchViewHolder>(ExploreDiffCallBack()) {

    lateinit var onClick: (Int, String) -> Unit

    inner class SearchViewHolder(val binding: SearchmovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            SearchmovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.exploreMoviesItem = item
        holder.binding.searchImg.setOnClickListener {
            item.id?.let {
                onClick(item.id, item.media_type)
            }
        }
    }

    class ExploreDiffCallBack : DiffUtil.ItemCallback<ResultX>() {
        override fun areItemsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
            return oldItem == newItem
        }
    }
}