package com.example.movaapp.ui.home

import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentHomeBinding
import com.example.movaapp.model.Result
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Array
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val viewPager = PopularMovieViewPager()
    private val topRatedMovieAdapter = TopRatedMovieAdapter()
    private val newReleaseMovieAdapter = NewReleaseMovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularMovies()
        binding.popularViewPager.adapter = viewPager
        binding.topRatedRV.adapter = topRatedMovieAdapter
        observeData()
        viewModel.getTopRatedMovies()
        viewModel.getNowPlayingMovies()
        binding.newReleasesRV.adapter = newReleaseMovieAdapter

    }

    private fun observeData() {
        viewModel.popularMovieState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.movies.results?.let {
                        viewPager.updateList(it)
                    }
                }

                is HomeUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is HomeUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
        viewModel.topRatedMovieState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.movies.results?.let {
                        topRatedMovieAdapter.updateList(it)
                        val topRatedList = it
                        binding.allTopRatedTxt.setOnClickListener {
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(topRatedList.toTypedArray(),"topRated"))
                        }
                    }
                }

                is HomeUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is HomeUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }

        viewModel.nowPlayingMovieState.observe(viewLifecycleOwner){
            when(it){
                is HomeUiState.Success->{
                    binding.loadingAnimation.gone()
                    it.movies.results?.let {
                        newReleaseMovieAdapter.updateList(it)
                        val nowPlayingList = it
                        binding.allNewReleasesTxt.setOnClickListener {
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(nowPlayingList.toTypedArray(),"nowPlaying"))
                        }
                    }
                }
                is HomeUiState.Error->{
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context,it.message,Toast.LENGTH_SHORT).show()
                }
                is HomeUiState.Loading->{
                    binding.loadingAnimation.visible()
                }
            }
        }
    }
}