package com.example.movaapp.ui.youtubeplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.YoutuberecommnedItemBinding
import com.example.movaapp.model.Result

class YoutubeRecommendAdapter :
    ListAdapter<Result, YoutubeRecommendAdapter.YoutubeRecommendViewHolder>(
        YoutubeRecommendDiffCallBack()
    ) {

    lateinit var onClick: (Int) -> Unit

    inner class YoutubeRecommendViewHolder(val binding: YoutuberecommnedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeRecommendViewHolder {
        val view =
            YoutuberecommnedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YoutubeRecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: YoutubeRecommendViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.recommendItem = item
        holder.binding.playClick.setOnClickListener {
            onClick(item.id)
        }
    }

    class YoutubeRecommendDiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}