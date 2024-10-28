package com.example.movaapp.ui.profile

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movaapp.R
import com.example.movaapp.base.BaseFragment
import com.example.movaapp.databinding.FragmentProfileBinding
import com.example.movaapp.utils.loadProfileImageUrl
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.hilt.android.AndroidEntryPoint
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

        with(binding) {
            edit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            save.setOnClickListener {
                savingData()
            }

            logOutBtn.setOnClickListener {
                showAlertDialog()
            }
        }

        getUserData()
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
                        "Saving is completed successfully",
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

    private fun logout() {
        val editor = sp.edit()
        editor.putBoolean("isLogin", false)
        editor.apply()
    }

    private fun getUserData() {
        val isSaving = sp.getBoolean("isSaving", false)
        if (isSaving) {
            val fullName = sp.getString("fullName", "fullname")
            val nickName = sp.getString("nickName", "nickname")
            val email = sp.getString("email", "email")
            val phoneNumber = sp.getString("phoneNumber", "Phone Number")
            val gender = sp.getString("gender", "Gender")
            with(binding) {
                fullNameInput.setText(fullName)

                nickNameInput.setText(nickName)

                emailProfileInput.setText(email)

                phoneNumberInput.setText(phoneNumber)

                genderInput.setText(gender)
            }

        }
    }

    private fun showAlertDialog() {
        this.context?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setTitle("Logout").setMessage("Do you want to log out?")
                .setPositiveButton("Yes") { dialog, _ ->
                    logout()
                    findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                    dialog.dismiss()
                }.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
        }
    }
}