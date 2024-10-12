package com.example.movaapp.ui.youtubeplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.YoutuberecommnedItemBinding
import com.example.movaapp.model.Result

class YoutubeRecommendAdapter:RecyclerView.Adapter<YoutubeRecommendAdapter.YoutubeRecommendViewHolder>() {

    val recommendList = ArrayList<Result>()
    lateinit var onClick:(Int)->Unit

    inner class YoutubeRecommendViewHolder(val binding:YoutuberecommnedItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeRecommendViewHolder {
        val view = YoutuberecommnedItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return YoutubeRecommendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }

    override fun onBindViewHolder(holder: YoutubeRecommendViewHolder, position: Int) {
        val item= recommendList[position]
        holder.binding.recommendItem = item
        holder.binding.playClick.setOnClickListener {
            onClick(item.id)
        }
    }

    fun updateList(newList:List<Result>){
        recommendList.clear()
        recommendList.addAll(newList)
        notifyDataSetChanged()
    }
}