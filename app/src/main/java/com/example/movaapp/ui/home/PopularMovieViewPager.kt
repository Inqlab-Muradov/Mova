package com.example.movaapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.PopularmoviesItemBinding
import com.example.movaapp.model.Result

class PopularMovieViewPager : RecyclerView.Adapter<PopularMovieViewPager.ViewPagerViewHolder>() {

    private val popularMoviesList = ArrayList<Result>()

    lateinit var onClick : (Int)->Unit
    lateinit var addMyList:(Result)->Unit
    lateinit var onClickPlay:(Int)->Unit

    inner class ViewPagerViewHolder(val binding: PopularmoviesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = PopularmoviesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return popularMoviesList.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = popularMoviesList[position]
        holder.binding.popularMovieItem = item
        holder.binding.posterMovie.setOnClickListener {
            onClick(item.id)
        }
        holder.binding.addMyList.setOnClickListener {
            addMyList(item)
        }
        holder.binding.playHome.setOnClickListener {
            Log.d("TAG", "onBindViewHolder() called")
            onClickPlay(item.id)
        }
    }

    fun updateList(newList:List<Result>){
        popularMoviesList.clear()
        popularMoviesList.addAll(newList)
        notifyDataSetChanged()
    }
}