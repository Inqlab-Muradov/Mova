package com.example.movaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.AllmoviesItemBinding
import com.example.movaapp.model.Result

class AllMoviesItemAdapter : RecyclerView.Adapter<AllMoviesItemAdapter.AllMoviesViewHolder>() {

    private val allMoviesList = ArrayList<Result>()
    lateinit var onClick:(Int)->Unit

    inner class AllMoviesViewHolder(val binding: AllmoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMoviesViewHolder {
        val view = AllmoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllMoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allMoviesList.size
    }

    override fun onBindViewHolder(holder: AllMoviesViewHolder, position: Int) {
        val item = allMoviesList[position]
        holder.binding.allMoviesItem = item
        holder.binding.allMoviesCard.setOnClickListener {
            onClick(item.id)
        }
    }

    fun updateList(newList: List<Result>) {
        allMoviesList.clear()
        allMoviesList.addAll(newList)
        notifyDataSetChanged()

    }
}