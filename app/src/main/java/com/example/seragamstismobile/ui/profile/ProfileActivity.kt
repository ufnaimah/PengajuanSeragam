package com.example.seragamstismobile.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadProfile()

        binding.btnUpdateProfile.setOnClickListener {
            // Logika Update Profile bisa ditambahkan di sini
            Toast.makeText(this, "Fitur update belum diimplementasikan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProfile() {
        lifecycleScope.launch {
            try {
                // Sekarang getProfile() tidak butuh parameter lagi karena sudah ada di ApiClient
                val response = ApiClient.getApiService(this@ProfileActivity).getProfile()

                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        binding.etName.setText(it.name)
                        binding.etEmail.setText(it.email)
                        binding.etIdentity.setText(it.identityNumber)
                    }
                } else {
                    Toast.makeText(this@ProfileActivity, "Gagal memuat profil: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error Koneksi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}