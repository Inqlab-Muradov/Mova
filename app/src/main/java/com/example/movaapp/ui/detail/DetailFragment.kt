package com.example.movaapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movaapp.R
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentDetailBinding
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate){

    val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = args.type
        if (type=="Movies"){
            viewModel.getMoviesDetail(args.id)
        }else if (type=="TvSeries"){
            viewModel.getTvSeriesDetail(args.id)
        }

        observeData()
        Log.e("id",args.id.toString())
    }

    private fun observeData(){
        viewModel.moviesDetailState.observe(viewLifecycleOwner){
            when(it){
                is DetailUiState.Success->{
                    binding.loadingAnimation.gone()
                    binding.moviesDetailItem = it.moviesDetailResponse
                }
                is DetailUiState.Error->{
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context,it.message,Toast.LENGTH_SHORT).show()
                }
                is DetailUiState.Loading->{
                    binding.loadingAnimation.visible()
                }
            }
        }

        viewModel.tvSeriesDetailState.observe(viewLifecycleOwner){
            when(it){
                is DetailUiState.Success->{
                    binding.moviesDetailItem =it.moviesDetailResponse
                    binding.releaseDateTxt.text = it.moviesDetailResponse.first_air_date
                    binding.originalNameTxt.text = it.moviesDetailResponse.original_name
                    binding.loadingAnimation.gone()
                }
                is DetailUiState.Error->{
                    Toast.makeText(this.context,it.message,Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }
                is DetailUiState.Loading->{
                    binding.loadingAnimation.visible()
                }
            }
        }
    }

}