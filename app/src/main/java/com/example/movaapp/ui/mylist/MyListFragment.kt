package com.example.movaapp.ui.mylist

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movaapp.R
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentMyListBinding
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>(FragmentMyListBinding::inflate) {

    private val viewModel by viewModels<MyListViewModel>()
    private val adapter = MyListItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        adapterWorks()
        buttonsSetup()
    }

    private fun buttonsSetup() {
        with(binding) {
            OnlyMoviesBtn.setOnClickListener {
                viewModel.filterMyList("movie")
                with(binding) {
                    OnlyMoviesBtn.setBackgroundColor(Color.parseColor("#E21221"))
                    OnlyMoviesBtn.setTextColor(Color.parseColor("#FFFFFFFF"))
                    AllCategoriesBtn.setBackgroundColor(Color.parseColor("#181A20"))
                    AllCategoriesBtn.setStrokeColorResource(R.color.red)
                    AllCategoriesBtn.setTextColor(Color.parseColor("#E21221"))
                    OnlyTvSeriesBtn.setBackgroundColor(Color.parseColor("#181A20"))
                    OnlyTvSeriesBtn.setStrokeColorResource(R.color.red)
                    OnlyTvSeriesBtn.setTextColor(Color.parseColor("#E21221"))
                }

            }
            OnlyTvSeriesBtn.setOnClickListener {
                viewModel.filterMyList("tv")
                with(binding) {
                    OnlyTvSeriesBtn.setBackgroundColor(Color.parseColor("#E21221"))
                    OnlyTvSeriesBtn.setTextColor(Color.parseColor("#FFFFFFFF"))
                    AllCategoriesBtn.setBackgroundColor(Color.parseColor("#181A20"))
                    AllCategoriesBtn.setStrokeColorResource(R.color.red)
                    AllCategoriesBtn.setTextColor(Color.parseColor("#E21221"))
                    OnlyMoviesBtn.setBackgroundColor(Color.parseColor("#181A20"))
                    OnlyMoviesBtn.setStrokeColorResource(R.color.red)
                    OnlyMoviesBtn.setTextColor(Color.parseColor("#E21221"))
                }

            }
            AllCategoriesBtn.setOnClickListener {
                viewModel.getAllMyListItem()
                with(binding) {
                    AllCategoriesBtn.setBackgroundColor(Color.parseColor("#E21221"))
                    AllCategoriesBtn.setTextColor(Color.parseColor("#FFFFFFFF"))
                    OnlyMoviesBtn.setBackgroundColor(Color.parseColor("#181A20"))
                    OnlyMoviesBtn.setStrokeColorResource(R.color.red)
                    OnlyMoviesBtn.setTextColor(Color.parseColor("#E21221"))
                    OnlyTvSeriesBtn.setBackgroundColor(Color.parseColor("#181A20"))
                    OnlyTvSeriesBtn.setStrokeColorResource(R.color.red)
                    OnlyTvSeriesBtn.setTextColor(Color.parseColor("#E21221"))
                }
            }
        }
    }

    private fun observeData() {
        viewModel.myListItemList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                with(binding) {
                    myListRV.visible()
                    emptyListImage.gone()
                    emptyListTxtFirst.gone()
                    emptyListTxtSecond.gone()
                }
            } else {
                with(binding) {
                    myListRV.gone()
                    emptyListImage.visible()
                    emptyListTxtFirst.visible()
                    emptyListTxtSecond.visible()
                }
            }
        }
        viewModel.filteredMyList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                with(binding) {
                    myListRV.visible()
                    emptyListImage.gone()
                    emptyListTxtFirst.gone()
                    emptyListTxtSecond.gone()
                }
            } else {
                with(binding) {
                    myListRV.gone()
                    emptyListImage.visible()
                    emptyListTxtFirst.visible()
                    emptyListTxtSecond.visible()
                }
            }
        }
    }

    private fun adapterWorks() {
        binding.myListRV.adapter = adapter
        adapter.onClick = { id, mediaType ->
            findNavController().navigate(
                MyListFragmentDirections.actionMyListFragmentToDetailFragment(
                    id,
                    mediaType
                )
            )
        }
    }

}