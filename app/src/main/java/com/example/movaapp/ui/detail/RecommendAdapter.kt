package com.example.movaapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.RecommendItemBinding
import com.example.movaapp.model.Result

class RecommendAdapter:RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    val recommendList = ArrayList<Result>()

    inner class RecommendViewHolder(val binding:RecommendItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = RecommendItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecommendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }
    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val item = recommendList[position]
        holder.binding.recommendItem = item
    }

    fun updateList(newList:List<Result>){
        recommendList.clear()
        recommendList.addAll(newList)
        notifyDataSetChanged()
    }
}