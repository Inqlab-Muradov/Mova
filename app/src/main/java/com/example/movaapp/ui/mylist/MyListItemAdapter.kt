package com.example.movaapp.ui.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.MylistItemBinding
import com.example.movaapp.local.MyListItem

class MyListItemAdapter :
    ListAdapter<MyListItem, MyListItemAdapter.MyListItemViewHolder>(MyListDiffCallBAck()) {

    lateinit var onClick: (Int, String) -> Unit

    inner class MyListItemViewHolder(val binding: MylistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListItemViewHolder {
        val view = MylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.allMyListItem = item
        holder.binding.allMoviesCard.setOnClickListener {
            onClick(item.id, item.mediaType)
        }
    }

    class MyListDiffCallBAck : DiffUtil.ItemCallback<MyListItem>() {
        override fun areItemsTheSame(oldItem: MyListItem, newItem: MyListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyListItem, newItem: MyListItem): Boolean {
            return oldItem == newItem
        }
    }

}