package com.example.movaapp.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.SearchmovieItemBinding
import com.example.movaapp.model.Result
import com.example.movaapp.model.ResultX

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchViewHolder>() {

    private val searchMovieList = ArrayList<ResultX>()
    lateinit var onClick: (Int,String) -> Unit

    inner class SearchViewHolder(val binding: SearchmovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =
            SearchmovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchMovieList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchMovieList[position]
        holder.binding.exploreMoviesItem = item
        holder.binding.searchImg.setOnClickListener {
            item.id?.let {
                onClick(item.id,item.media_type)
            }
        }
    }


    fun updateList(newList: List<ResultX>) {
        searchMovieList.clear()
        searchMovieList.addAll(newList)
        notifyDataSetChanged()
    }
}