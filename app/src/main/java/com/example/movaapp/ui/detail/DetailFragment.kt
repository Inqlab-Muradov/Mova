package com.example.movaapp.ui.detail

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movaapp.R
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentDetailBinding
import com.example.movaapp.local.MyListItem
import com.example.movaapp.ui.home.AllMoviesItemAdapter
import com.example.movaapp.ui.home.HomeUiState
import com.example.movaapp.ui.home.TopRatedMovieAdapter
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.loadImageUrl
import com.example.movaapp.utils.visible
import com.example.movaapp.utils.youtube_api_key
import com.google.android.material.button.MaterialButton.OnCheckedChangeListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private val recommendAdapter = RecommendAdapter()
    private val reviewAdapter = ReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaTypeCalls(args.id)
        observeData()
        checkMyListItem()
        adapterSetup()
        cardsSetup()
    }

    private fun mediaTypeCalls(id: Int) {
        val type = args.type
        if (type == "movie") {
            viewModel.initialMovieCalls(id)
        } else if (type == "tv") {
            viewModel.initialTvSeriesCall(id)
        }
    }


    private fun checkMyListItem() {
        viewModel.getMyListById(args.id)
        viewModel.myListItemById.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.addMyListDetail.isChecked = true
            }
        }
    }

    private fun adapterSetup() {
        binding.commentsRV.adapter = reviewAdapter
        binding.moreLikeThisRV.adapter = recommendAdapter
        recommendAdapter.onClick = {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentSelf3(
                    it,
                    args.type
                )
            )
        }
    }

    private fun cardsSetup() {
        with(binding) {
            commentsCard.setOnClickListener {
                with(binding) {
                    moreLikeThisRV.gone()
                    commentsRV.visible()
                    commentsTxt.setTextColor(Color.RED)
                    commentsLine.setBackgroundColor(Color.RED)
                    moreLikeThisTxt.setTextColor(Color.GRAY)
                    moreLikeThisLine.setBackgroundColor(Color.GRAY)
                }
            }
            moreLikeThisCard.setOnClickListener {
                with(binding) {
                    moreLikeThisRV.visible()
                    commentsRV.gone()
                    commentsTxt.setTextColor(Color.GRAY)
                    commentsLine.setBackgroundColor(Color.GRAY)
                    moreLikeThisTxt.setTextColor(Color.RED)
                    moreLikeThisLine.setBackgroundColor(Color.RED)
                }
            }
        }
    }

    private fun observeData() {
        viewModel.moviesDetailState.observe(viewLifecycleOwner) { item ->
            when (item) {
                is DetailUiState.Success -> {
                    binding.loadingAnimation.gone()
                    binding.moviesDetailItem = item.moviesDetailResponse
                    binding.originalNameTxt.text = item.moviesDetailResponse.original_title
                    item.moviesDetailResponse.release_date?.let {
                        binding.releaseDateTxt.text = it.substring(0, 4)
                    }
                    item.moviesDetailResponse.poster_path?.let {
                        val myListItem = MyListItem(
                            item.moviesDetailResponse.id,
                            it,
                            item.moviesDetailResponse.vote_average,
                            "movie"
                        )
                        binding.addMyListDetail.setOnClickListener {
                            if (binding.addMyListDetail.isChecked) viewModel.addMyListItem(
                                myListItem
                            )
                            else viewModel.deleteMyListItem(myListItem)
                        }
                    }
                }

                is DetailUiState.Error -> {
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context, item.message, Toast.LENGTH_SHORT).show()
                }

                is DetailUiState.Loading -> {
                    binding.loadingAnimation.visible()
                }
            }
        }

        viewModel.tvSeriesDetailState.observe(viewLifecycleOwner) { item ->
            when (item) {
                is DetailUiState.Success -> {
                    binding.moviesDetailItem = item.moviesDetailResponse
                    binding.loadingAnimation.gone()
                    item.moviesDetailResponse.poster_path?.let {
                        val myListItem = MyListItem(
                            item.moviesDetailResponse.id,
                            it,
                            item.moviesDetailResponse.vote_average,
                            "tv"
                        )
                        binding.addMyListDetail.setOnClickListener {
                            if (binding.addMyListDetail.isChecked) viewModel.addMyListItem(
                                myListItem
                            )
                            else viewModel.deleteMyListItem(myListItem)
                        }
                    }
                }

                is DetailUiState.Error -> {
                    Toast.makeText(this.context, item.message, Toast.LENGTH_SHORT).show()
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
        viewModel.moviesVideoResponse.observe(viewLifecycleOwner){
            when(it){
                is DetailViewModel.MoviesVideoUiState.Success->{
                    binding.loadingAnimation.gone()
                    it.response.results?.let {videoList->
                        for(i in videoList){
                            if (i.type=="Trailer"){
                                val videoId = i.key
                                videoId?.let {
                                    binding.playDetail.setOnClickListener {
                                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToYoutubePlayerFragment(videoId))
                                    }
                                }
                            }
                        }
                    }
                }
                is DetailViewModel.MoviesVideoUiState.Error->{
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context,it.message,Toast.LENGTH_SHORT).show()
                }
                is DetailViewModel.MoviesVideoUiState.Loading->{
                    binding.loadingAnimation.visible()
                }
            }
        }
        viewModel.tvSeriesVideoResponse.observe(viewLifecycleOwner){
            when(it){
                is DetailViewModel.MoviesVideoUiState.Success->{
                    binding.loadingAnimation.gone()
                    it.response.results?.let {videoList->
                        for(i in videoList){
                            if (i.type=="Trailer"){
                                val videoId = i.key
                                videoId?.let {
                                    binding.playDetail.setOnClickListener {
                                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToYoutubePlayerFragment(videoId))
                                    }
                                }
                            }
                        }
                    }
                }
                is DetailViewModel.MoviesVideoUiState.Error->{
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context,it.message,Toast.LENGTH_SHORT).show()
                }
                is DetailViewModel.MoviesVideoUiState.Loading->{
                    binding.loadingAnimation.visible()
                }
            }
        }
    }

}