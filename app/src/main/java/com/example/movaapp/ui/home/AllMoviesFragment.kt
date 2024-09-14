package com.example.movaapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentAllMoviesBinding


class AllMoviesFragment :
    BaseFragment<FragmentAllMoviesBinding>(FragmentAllMoviesBinding::inflate) {

    private val args by navArgs<AllMoviesFragmentArgs>()
    private val allMoviesAdapter = AllMoviesItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.allMoviesRV.adapter = allMoviesAdapter
        binding.backAllMovies.setOnClickListener {
            findNavController().popBackStack()
        }

        if (args.name == "topRated") {
            binding.allMoviesTitle.text = "Top Rated Movies"
        } else if (args.name == "nowPlaying") {
            binding.allMoviesTitle.text = "New Releases "
        }
        allMoviesAdapter.updateList(args.list.toList())

        allMoviesAdapter.onClick={
            findNavController().navigate(AllMoviesFragmentDirections.actionAllMoviesFragmentToDetailFragment(it,"Movies"))
        }
    }

}