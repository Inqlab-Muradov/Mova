package com.example.movaapp.ui.detail

import android.graphics.Color
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
import com.example.movaapp.ui.home.AllMoviesItemAdapter
import com.example.movaapp.ui.home.HomeUiState
import com.example.movaapp.ui.home.TopRatedMovieAdapter
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.loadImageUrl
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private val recommendAdapter = RecommendAdapter()
    private val reviewAdapter = ReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = args.type
        if (type == "Movies") {
            viewModel.getMoviesDetail(args.id)
            viewModel.getMovieCredits(args.id)
            viewModel.getMovieRecommendations(args.id)
            viewModel.getMovieReviews(args.id)
        } else if (type == "movie") {
            viewModel.getMoviesDetail(args.id)
            viewModel.getMovieCredits(args.id)
            viewModel.getMovieRecommendations(args.id)
            viewModel.getMovieReviews(args.id)
        } else if (type == "tv") {
            viewModel.getTvSeriesDetail(args.id)
            viewModel.getTvSeriesName(args.id)
            viewModel.getTvSeriesCredits(args.id)
            viewModel.getTvSeriesRecommend(args.id)
            viewModel.getTvSeriesReviews(args.id)
        }
        binding.moreLikeThisRV.adapter = recommendAdapter
        observeData()
        binding.commentsRV.adapter = reviewAdapter

        binding.commentsCard.setOnClickListener {
            binding.moreLikeThisRV.gone()
            binding.commentsRV.visible()
            binding.commentsTxt.setTextColor(Color.RED)
            binding.commentsLine.setBackgroundColor(Color.RED)
            binding.moreLikeThisTxt.setTextColor(Color.GRAY)
            binding.moreLikeThisLine.setBackgroundColor(Color.GRAY)
        }

        binding.moreLikeThisCard.setOnClickListener {
            binding.moreLikeThisRV.visible()
            binding.commentsRV.gone()
            binding.commentsTxt.setTextColor(Color.GRAY)
            binding.commentsLine.setBackgroundColor(Color.GRAY)
            binding.moreLikeThisTxt.setTextColor(Color.RED)
            binding.moreLikeThisLine.setBackgroundColor(Color.RED)
        }
    }

    private fun observeData() {
        viewModel.moviesDetailState.observe(viewLifecycleOwner) {
            when (it) {
                is DetailUiState.Success -> {
                    binding.loadingAnimation.gone()
                    binding.moviesDetailItem = it.moviesDetailResponse
                    binding.originalNameTxt.text = it.moviesDetailResponse.original_title
                    it.moviesDetailResponse.release_date?.let {
                        binding.releaseDateTxt.text = it.substring(0, 4)
                    }
                }

                is DetailUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is DetailUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }

        viewModel.tvSeriesDetailState.observe(viewLifecycleOwner) {
            when (it) {
                is DetailUiState.Success -> {
                    binding.moviesDetailItem = it.moviesDetailResponse
                    binding.loadingAnimation.gone()
                }

                is DetailUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is DetailUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
        viewModel.movieCreditsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.MovieCreditsUiState.Success -> {
                    it.response.crew?.let {
                        it[1].profile_path?.let {
                            binding.directorPhoto.loadImageUrl(it)
                        }
                        it[1].name?.let {
                            binding.directorName.text = it
                        }
                    }
                    binding.moviesCreditsItem = it.response
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieCreditsUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieCreditsUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
        viewModel.tvSeriesNameResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.TvSeriesNameState.Success -> {
                    binding.loadingAnimation.gone()
                    it.response.original_name?.let {
                        binding.originalNameTxt.text = it
                    }
                    it.response.first_air_date?.let {
                        binding.releaseDateTxt.text = it.substring(0, 4)
                    }
                }

                is DetailViewModel.TvSeriesNameState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }

                is DetailViewModel.TvSeriesNameState.Loading -> {
                    binding.loadingAnimation.visibility
                }
            }
        }

        viewModel.tvSeriesCreditsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.MovieCreditsUiState.Success -> {
                    it.response.crew?.let {
                        try {
                            it[5].profile_path?.let {
                                binding.directorPhoto.loadImageUrl(it)
                            }
                            it[5].name?.let {
                                binding.directorName.text = it
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                    binding.moviesCreditsItem = it.response
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieCreditsUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieCreditsUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
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
        viewModel.movieReviewResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.MovieReviewsUiState.Success -> {
                    it.response.results?.let {
                        reviewAdapter.updateList(it)
                    }
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieReviewsUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieReviewsUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
        viewModel.tvSeriesReviewsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.MovieReviewsUiState.Success -> {
                    it.response.results?.let {
                        reviewAdapter.updateList(it)
                    }
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieReviewsUiState.Error -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.loadingAnimation.gone()
                }

                is DetailViewModel.MovieReviewsUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }
    }

}