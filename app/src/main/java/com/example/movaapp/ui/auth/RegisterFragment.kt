package com.example.movaapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentRegisterBinding
import com.example.movaapp.utils.gone
import com.example.movaapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            backRegister.setOnClickListener {
                findNavController().popBackStack()
            }
            signInTxt.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
            signUpBtn.setOnClickListener {
                register()
            }
        }
        observeData()
    }

    private fun observeData(){
        viewModel.authResult.observe(viewLifecycleOwner){
            when(it){
                is AuthUiState.Success->{
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context,"Register is Successful", Toast.LENGTH_SHORT).show()
                }
                is AuthUiState.Error->{
                    binding.loadingAnimation.gone()
                    Toast.makeText(this.context,it.message, Toast.LENGTH_SHORT).show()
                }
                is AuthUiState.Loading->{
                    binding.loadingAnimation.visible()
                }
            }
        }
    }

    private fun register(){
        val email = binding.emailInput.editText?.text.toString()
        val password = binding.passwordTxtInput.editText?.text.toString()
        if (email.isNotEmpty()&&password.isNotEmpty()){
            viewModel.register(email,password)
        }else{
            Toast.makeText(this.context,"Please fill all fields", Toast.LENGTH_SHORT).show()

        }
    }
}