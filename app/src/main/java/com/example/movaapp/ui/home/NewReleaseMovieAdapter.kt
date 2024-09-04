package com.example.movaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.NewreleasemoviesItemBinding
import com.example.movaapp.model.Result

class NewReleaseMovieAdapter :
    RecyclerView.Adapter<NewReleaseMovieAdapter.NewReleaseMovieViewHolder>() {

    private val newReleaseMovieList = ArrayList<Result>()

    inner class NewReleaseMovieViewHolder(val binding: NewreleasemoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewReleaseMovieViewHolder {
        val view =
            NewreleasemoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewReleaseMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newReleaseMovieList.size
    }

    override fun onBindViewHolder(holder: NewReleaseMovieViewHolder, position: Int) {
        val item = newReleaseMovieList[position]
        holder.binding.newReleaseItem = item

    }

    fun updateList(newList: List<Result>) {
        newReleaseMovieList.clear()
        newReleaseMovieList.addAll(newList)
        notifyDataSetChanged()

    }
}