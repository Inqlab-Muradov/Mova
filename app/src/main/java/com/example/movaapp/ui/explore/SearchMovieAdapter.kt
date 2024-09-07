package com.example.movaapp.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.SearchmovieitemBinding
import com.example.movaapp.model.Result

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchViewHolder>() {

    private val searchMovieList = ArrayList<Result>()

    inner class SearchViewHolder(val binding:SearchmovieitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = SearchmovieitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchMovieList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchMovieList[position]
        holder.binding.searchMovieItem = item
    }


    fun updateList(newList:List<Result>){
        searchMovieList.clear()
        searchMovieList.addAll(newList)
        notifyDataSetChanged()
    }
}