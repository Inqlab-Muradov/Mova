package com.example.movaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.TopratedmoviesItemBinding
import com.example.movaapp.model.Result

class TopRatedMovieAdapter:RecyclerView.Adapter<TopRatedMovieAdapter.TopMovieViewHolder>() {

    val topMoviesList = ArrayList<Result>()
    lateinit var onClick: (Int)-> Unit

    inner class TopMovieViewHolder(val binding:TopratedmoviesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val view = TopratedmoviesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topMoviesList.size
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
        val item = topMoviesList[position]
        holder.binding.topRatedMovieItem = item
        holder.binding.topRatedMovieImg.setOnClickListener {
            onClick(item.id)
        }
    }

    fun updateList(newList:List<Result>){
        topMoviesList.clear()
        topMoviesList.addAll(newList)
        notifyDataSetChanged()

    }

}