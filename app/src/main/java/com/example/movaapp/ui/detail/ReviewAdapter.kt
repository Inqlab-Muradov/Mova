package com.example.movaapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movaapp.databinding.ReviewItemBinding
import com.example.movaapp.model.ResultReviews

class ReviewAdapter:RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviewList = ArrayList<ResultReviews>()

    inner class ReviewViewHolder(val binding:ReviewItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = ReviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = reviewList[position]
        holder.binding.reviewItem = item
    }

    fun updateList(newList:List<ResultReviews>){
        reviewList.clear()
        reviewList.addAll(newList)
        notifyDataSetChanged()
    }
}