package com.example.seragamstismobile.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityProfileBinding
import com.example.seragamstismobile.model.UpdateProfileRequest
import com.example.seragamstismobile.ui.login.LoginActivity
import com.example.seragamstismobile.util.SessionManager
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        loadProfile()

        // Handler Update Profil
        binding.btnUpdateProfile.setOnClickListener {
            val newName = binding.etName.text.toString().trim()
            if (newName.isNotEmpty()) {
                performUpdateProfile(newName)
            }
        }

        // Handler Hapus Akun
        binding.btnDeleteAccount?.setOnClickListener {
            performDeleteAccount()
        }
    }

    private fun loadProfile() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProfileActivity).getProfile()
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        binding.etName.setText(it.namaLengkap) // Sudah sesuai Model baru
                        binding.tvNim.text = "NIM: ${it.nim}" // Sudah sesuai Model baru
                        binding.etEmail.setText(it.email)
                    }
                } else {
                    Toast.makeText(this@ProfileActivity, "Gagal memuat profil", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performUpdateProfile(name: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProfileActivity)
                    .updateProfile(UpdateProfileRequest(name))
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Profil diperbarui!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Gagal update", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performDeleteAccount() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProfileActivity).deleteAccount()
                if (response.isSuccessful) {
                    sessionManager.clearSession() // Sudah sesuai SessionManager baru
                    val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Gagal menghapus akun", Toast.LENGTH_SHORT).show()
            }
        }
    }
}