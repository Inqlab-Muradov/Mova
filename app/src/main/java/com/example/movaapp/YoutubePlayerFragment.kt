package com.example.movaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentYoutubePlayerBinding
import com.example.movaapp.ui.home.HomeFragmentDirections
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener


class YoutubePlayerFragment : BaseFragment<FragmentYoutubePlayerBinding>(FragmentYoutubePlayerBinding::inflate) {

    private val args by navArgs<YoutubePlayerFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(args.videoId, 0F)
            }
        })
        binding.backYoutube.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}