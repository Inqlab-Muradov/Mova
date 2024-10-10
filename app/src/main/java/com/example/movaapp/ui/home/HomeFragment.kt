package com.example.movaapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentHomeBinding
import com.example.movaapp.local.MyListItem
import com.example.movaapp.ui.detail.DetailFragmentDirections
import com.example.movaapp.ui.detail.DetailViewModel
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val viewPager = PopularMovieViewPager()
    private val topRatedMovieAdapter = TopRatedMovieAdapter()
    private val newReleaseMovieAdapter = NewReleaseMovieAdapter()

    private lateinit var currentVideoId:String

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

        viewPager.onClickPlay={itemID->
            currentVideoId = ""
            viewModel.getMovieVideo(itemID)
            viewModel.moviesVideoResponse.observe(viewLifecycleOwner){videoResponse->
                when(videoResponse){
                    is DetailViewModel.MoviesVideoUiState.Success->{
                        binding.loadingAnimation.gone()
                        videoResponse.response.results?.let {videoList->
                            for(i in videoList){
                                if (i.type=="Trailer"){
                                    i.key?.let {key->
                                        currentVideoId = key
                                    }
                                }
                            }
                        }
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToYoutubePlayerFragment(currentVideoId))
                        viewModel.moviesVideoResponse.removeObservers(viewLifecycleOwner)

                    }
                    is DetailViewModel.MoviesVideoUiState.Error->{
                        binding.loadingAnimation.gone()
                        Toast.makeText(this.context,videoResponse.message,Toast.LENGTH_SHORT).show()

                    }
                    is DetailViewModel.MoviesVideoUiState.Loading->{
                        binding.loadingAnimation.visible()
                    }
                }
            }
        }
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
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToAllMoviesFragment(
                                    topRatedList.toTypedArray(),
                                    "topRated"
                                )
                            )
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

        viewModel.nowPlayingMovieState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Success -> {
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

                is HomeUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is HomeUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }


    }

    private fun adapterFunctions() {
        viewPager.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,"movie"
                )
            )
        }
        newReleaseMovieAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,"movie"
                )
            )
        }
        topRatedMovieAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it,"movie"
                )
            )
        }
        viewPager.addMyList={item->
            item.poster_path?.let {
                val posterPath = it
                val myListItem = MyListItem(item.id,posterPath,item.vote_average,"movie")
                viewModel.addMyList(myListItem)
            }
        }

    }


}
