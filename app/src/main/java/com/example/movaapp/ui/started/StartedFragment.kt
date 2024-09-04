package com.example.movaapp.ui.started

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentStartedBinding


class StartedFragment : BaseFragment<FragmentStartedBinding>(FragmentStartedBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            backStarted.setOnClickListener {
                findNavController().popBackStack()
            }
            signUpTxt.setOnClickListener {
                findNavController().navigate(StartedFragmentDirections.actionStartedFragmentToRegisterFragment2())
            }
            signInButton.setOnClickListener {
                findNavController().navigate(StartedFragmentDirections.actionStartedFragmentToLoginFragment())
            }
        }
    }
}