package com.example.movaapp.ui.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.MylistItemBinding
import com.example.movaapp.local.MyListItem

class MyListItemAdapter : RecyclerView.Adapter<MyListItemAdapter.MyListItemViewHolder>() {

    private val myListItemList = ArrayList<MyListItem>()
    lateinit var onClick: (Int, String) -> Unit

    inner class MyListItemViewHolder(val binding: MylistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListItemViewHolder {
        val view = MylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myListItemList.size
    }

    override fun onBindViewHolder(holder: MyListItemViewHolder, position: Int) {
        val item = myListItemList[position]
        holder.binding.allMyListItem = item
        holder.binding.allMoviesCard.setOnClickListener {
            onClick(item.id, item.mediaType)
        }
    }

    fun updateList(newList: List<MyListItem>) {
        myListItemList.clear()
        myListItemList.addAll(newList)
        notifyDataSetChanged()
    }
}