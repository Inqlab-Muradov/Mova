package com.example.movaapp.ui.welcome

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    @Inject lateinit var sp:SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getStartedButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToStartedFragment())
        }
        getSharedPreferences()
    }

    private fun getSharedPreferences(){
        val login = sp.getBoolean("isLogin",false)
        if (login) {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment())
        }
    }
}