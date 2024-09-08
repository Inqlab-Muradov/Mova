package com.example.movaapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentHomeBinding
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val viewPager = PopularMovieViewPager()
    private val topRatedMovieAdapter = TopRatedMovieAdapter()
    private val newReleaseMovieAdapter = NewReleaseMovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            popularViewPager.adapter = viewPager
            topRatedRV.adapter = topRatedMovieAdapter
            newReleasesRV.adapter = newReleaseMovieAdapter
        }
        with(viewModel) {
            getTopRatedMovies()
            getNowPlayingMovies()
            getPopularMovies()
        }
        adapterFunctions()
        observeData()
    }

    private fun observeData() {
        viewModel.popularMovieState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.movies.results?.let {
                        viewPager.updateList(it)
                    }
                }

                is UiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
        viewModel.topRatedMovieState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.movies.results?.let {
                        topRatedMovieAdapter.updateList(it)
                        val topRatedList = it
                        binding.allTopRatedTxt.setOnClickListener {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(
                                    topRatedList.toTypedArray(),
                                    "topRated"
                                )
                            )
                        }
                    }
                }

                is UiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }

        viewModel.nowPlayingMovieState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.loadingAnimation.gone()
                    it.movies.results?.let {
                        newReleaseMovieAdapter.updateList(it)
                        val nowPlayingList = it
                        binding.allNewReleasesTxt.setOnClickListener {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(
                                    nowPlayingList.toTypedArray(),
                                    "nowPlaying"
                                )
                            )
                        }
                    }
                }

                is UiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
    }

    private fun adapterFunctions() {
        viewPager.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,"Movies"
                )
            )
        }
        newReleaseMovieAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,"Movies"
                )
            )
        }
        topRatedMovieAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,"Movies"
                )
            )
        }
    }

}