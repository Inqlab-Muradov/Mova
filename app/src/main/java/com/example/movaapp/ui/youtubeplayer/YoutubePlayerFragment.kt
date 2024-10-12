package com.example.movaapp.ui.youtubeplayer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentYoutubePlayerBinding
import com.example.movaapp.ui.detail.DetailFragmentDirections
import com.example.movaapp.ui.detail.DetailViewModel
import com.example.movaapp.ui.detail.RecommendAdapter
import com.example.movaapp.ui.home.HomeFragmentDirections
import com.example.movaapp.ui.home.HomeUiState
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoutubePlayerFragment :
    BaseFragment<FragmentYoutubePlayerBinding>(FragmentYoutubePlayerBinding::inflate) {

    private val args by navArgs<YoutubePlayerFragmentArgs>()
    private val recommendAdapter = YoutubeRecommendAdapter()
    private val viewModel by viewModels<YoutubePlayerViewModel>()

    private lateinit var currentVideoId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(args.videoId, 0F)
            }
        })
        binding.backYoutube.setOnClickListener {
            findNavController().navigate(
                YoutubePlayerFragmentDirections.actionYoutubePlayerFragmentToHomeFragment()
            )
        }
        binding.recommendRv.adapter = recommendAdapter
        val mediaType = args.mediaType
        if (mediaType == "movie") {
            viewModel.getMovieRecommendations(args.id.toInt())
        } else if (mediaType == "tv") {
            viewModel.getTvSeriesRecommend(args.id.toInt())
        }
        observeData()
        recommendAdapter.onClick = {
            if (mediaType == "movie") {
                viewModel.getMoviesVideo(it)
            } else if (mediaType == "tv") {
                viewModel.getTvSeriesVideo(it)
            }
        }
    }

    private fun observeData() {
        viewModel.movieRecommend.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Success -> {
                    it.movies.results?.let {
                        recommendAdapter.updateList(it)
                    }
                    binding.loadingAnimation.gone()
                }

                is HomeUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is HomeUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }

        viewModel.tvSeriesRecommendResponse.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Success -> {
                    it.movies.results?.let {
                        recommendAdapter.updateList(it)
                    }
                    binding.loadingAnimation.gone()
                }

                is HomeUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is HomeUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }

        if (args.mediaType == "movie") {
            viewModel.moviesVideoResponse.observe(viewLifecycleOwner) { videoResponse ->
                when (videoResponse) {
                    is DetailViewModel.MoviesVideoUiState.Success -> {
                        var movieId = ""
                        binding.loadingAnimation.gone()
                        videoResponse.response.id?.let {
                            movieId = it.toString()
                        }
                        videoResponse.response.results?.let { videoList ->
                            videoList.filter {
                                it.type == "Trailer"
                            }.forEach { list ->
                                currentVideoId = list.key ?: ""
                            }
                        }
                        findNavController().navigate(
                            YoutubePlayerFragmentDirections.actionYoutubePlayerFragmentSelf(
                                currentVideoId,
                                movieId,
                                "movie"
                            )
                        )

                    }

                    is DetailViewModel.MoviesVideoUiState.Error -> {
                        binding.loadingAnimation.gone()
                        Toast.makeText(this.context, videoResponse.message, Toast.LENGTH_SHORT)
                            .show()

                    }

                    is DetailViewModel.MoviesVideoUiState.Loading -> {
                        binding.loadingAnimation.visible()
                    }
                }
            }
        } else if (args.mediaType == "tv") {
            viewModel.tvSeriesVideoResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is DetailViewModel.MoviesVideoUiState.Success -> {
                        var tvSeriesId = ""
                        binding.loadingAnimation.gone()
                        it.response.id?.let {
                            tvSeriesId = it.toString()
                        }
                        it.response.results?.let { videoList ->
                            videoList.filter {
                                it.type == "Trailer"
                            }.forEach { list ->
                                currentVideoId = list.key ?: ""
                            }
                            findNavController().navigate(
                                YoutubePlayerFragmentDirections.actionYoutubePlayerFragmentSelf(
                                    currentVideoId,
                                    tvSeriesId,
                                    "tv"
                                )
                            )
                        }
                    }

                    is DetailViewModel.MoviesVideoUiState.Error -> {
                        binding.loadingAnimation.gone()
                        Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    }

                    is DetailViewModel.MoviesVideoUiState.Loading -> {
                        binding.loadingAnimation.visible()
                    }
                }
            }
        }
    }

}