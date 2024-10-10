package com.example.movaapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentProfileBinding
import com.example.movaapp.utils.imageBase_url
import com.example.movaapp.utils.loadImageUrl
import com.example.movaapp.utils.loadProfileImageUrl
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    @Inject
    lateinit var sp: SharedPreferences

    private val storage = Firebase.storage
    private val reference = storage.reference
    private val imageReference = reference.child("profilePhoto")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoPickerSetup()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isSaving = sp.getBoolean("isSaving", false)
        if (isSaving) {
            val fullName = sp.getString("fullName", "fullname")
            binding.fullNameInput.setText(fullName)
            val nickName = sp.getString("nickName", "nickname")
            binding.nickNameInput.setText(nickName)
            val email = sp.getString("email", "email")
            binding.emailProfileInput.setText(email)
            val phoneNumber = sp.getString("phoneNumber", "Phone Number")
            binding.phoneNumberInput.setText(phoneNumber)
            val gender = sp.getString("gender", "Gender")
            binding.genderInput.setText(gender)
        }

        binding.edit.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.save.setOnClickListener {
            savingData()
        }

        getProfilePhoto()
    }

    private fun getProfilePhoto() {
        imageReference.metadata.addOnSuccessListener {
            try {
                lifecycleScope.launch {
                    val url = imageReference.downloadUrl.await()
                    url?.let {
                        binding.editingPP.loadProfileImageUrl(it.toString())
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            binding.editingPP.setImageResource(R.drawable.editingpp)
        }
    }

    private fun saveProfilePhoto(uri: Uri) {
        try {
            lifecycleScope.launch {
                imageReference.putFile(uri).await()
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun photoPickerSetup() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.editingPP.setImageURI(uri)
                binding.save.setOnClickListener {
                    saveProfilePhoto(uri)
                    Toast.makeText(
                        this.context,
                        "Profile Photo is successfully changed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this.context, "Media is not selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savingData() {
        val fullName = binding.fullNameInput.text.toString()
        val nickName = binding.nickNameInput.text.toString()
        val email = binding.emailProfileInput.text.toString()
        val phoneNumber = binding.phoneNumberInput.text.toString()
        val gender = binding.genderInput.text.toString()

        if (fullName.isNotEmpty() && nickName.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && gender.isNotEmpty()) {
            val editor = sp.edit()
            editor.putString("fullName", fullName)
            editor.putString("nickName", nickName)
            editor.putString("email", email)
            editor.putString("phoneNumber", phoneNumber)
            editor.putString("gender", gender)
            editor.putBoolean("isSaving", true)
            editor.apply()
            Toast.makeText(this.context, "Saving is completed successfully", Toast.LENGTH_SHORT)
                .show()
        } else Toast.makeText(this.context, "Fill all the items", Toast.LENGTH_SHORT).show()
    }
}