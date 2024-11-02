package com.example.movaapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.RecommendItemBinding
import com.example.movaapp.model.Result

class RecommendAdapter :
    androidx.recyclerview.widget.ListAdapter<Result, RecommendAdapter.RecommendViewHolder>(
        RecommendDiffCallBack()
    ) {

    lateinit var onClick: (Int) -> Unit

    inner class RecommendViewHolder(val binding: RecommendItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = RecommendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.recommendItem = item
        holder.binding.recommendItemsCard.setOnClickListener {
            onClick(item.id)
        }
    }

    class RecommendDiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }


}