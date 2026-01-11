package com.example.seragamstismobile.ui.login

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityRegisterBinding
import com.example.seragamstismobile.model.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Dropdown Gender
        val genders = arrayOf("LAKI_LAKI", "PEREMPUAN")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genders)
        binding.actvGender.setAdapter(adapter)

        binding.btnRegister.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {
        val username = binding.etUsername.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        val nim = binding.etNim.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val gender = binding.actvGender.text.toString().trim()
        val isHijab = binding.switchHijab.isChecked

        // Validasi input
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || nim.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        val request = RegisterRequest(
            username = username,
            password = password,
            namaLengakap = name, // Pastikan sesuai field di JSON
            nim = nim,
            gender = gender,
            isHijab = isHijab
        )

        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@RegisterActivity).register(request)
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Terjadi kesalahan"
                    Toast.makeText(this@RegisterActivity, "Gagal: $errorBody", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}