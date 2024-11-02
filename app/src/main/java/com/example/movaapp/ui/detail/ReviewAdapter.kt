package com.example.movaapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.ReviewItemBinding
import com.example.movaapp.model.ResultReviews

class ReviewAdapter:ListAdapter<ResultReviews,ReviewAdapter.ReviewViewHolder>(ReviewsDiffCallBack()) {

    inner class ReviewViewHolder(val binding:ReviewItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = ReviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.reviewItem = item
    }

    class ReviewsDiffCallBack:DiffUtil.ItemCallback<ResultReviews>() {
        override fun areItemsTheSame(oldItem: ResultReviews, newItem: ResultReviews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultReviews, newItem: ResultReviews): Boolean {
            return oldItem==newItem
        }
    }
}