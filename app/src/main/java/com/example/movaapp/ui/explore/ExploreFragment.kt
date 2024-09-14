package com.example.movaapp.ui.explore

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movaapp.R
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentExploreBinding
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private val exploreAdapter = SearchMovieAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTrendingTv()
        binding.searchRV.adapter = exploreAdapter
        searchTextChangeListener()
        observeData()
        trendingTvSeriesObserver()
        exploreAdapter.onClick = {id,media->
            findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToDetailFragment(id,media
                )
            )
        }

    }

    private fun searchTextChangeListener() {
        binding.searchTxt.addTextChangedListener {
            it?.let {
                if (it.isEmpty()) {
                    trendingTvSeriesObserver()
                } else {
                    viewModel.getSearchMovies(it.toString())
                }

            }
        }
    }


    private fun observeData() {
        viewModel.searchMoviesState.observe(viewLifecycleOwner) {
            when (it) {
                is ExploreUiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.result.results?.let {
                        if (it.isNotEmpty()) {
                            exploreAdapter.updateList(it)
                            binding.notFoundCard.gone()
                            binding.searchRV.visible()
                            val icon = binding.searchTxt.compoundDrawables[0]
                            icon.setTint(Color.parseColor("#757575"))
                            binding.searchTxt.setBackgroundResource(R.drawable.search_shape)
                        } else {
                            binding.notFoundCard.visible()
                            binding.searchRV.gone()
                            val icon = binding.searchTxt.compoundDrawables[0]
                            icon.setTint(Color.parseColor("#E21221"))
                            binding.searchTxt.setBackgroundResource(R.drawable.search_not_founded)
                        }
                    }
                }

                is ExploreUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is ExploreUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
    }


    private fun trendingTvSeriesObserver() {
        viewModel.trendingTvSeriesState.observe(viewLifecycleOwner) {
            when (it) {
                is ExploreUiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.result.results?.let {
                        exploreAdapter.updateList(it)
                        binding.notFoundCard.gone()
                    }
                }

                is ExploreUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is ExploreUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
    }

}