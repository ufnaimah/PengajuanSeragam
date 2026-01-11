package com.example.seragamstismobile.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityProfileBinding
import com.example.seragamstismobile.model.ChangePasswordRequest
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

        binding.btnUpdateProfile.setOnClickListener {
            val newName = binding.etName.text.toString().trim()
            if (newName.isNotEmpty()) updateProfile(newName)
        }

        binding.btnChangePassword.setOnClickListener {
            val oldPass = binding.etOldPassword.text.toString().trim()
            val newPass = binding.etNewPassword.text.toString().trim()
            if (oldPass.isNotEmpty() && newPass.length >= 6) {
                performChangePassword(oldPass, newPass)
            } else {
                Toast.makeText(this, "Password baru min 6 karakter", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            performDeleteAccount()
        }
    }

    private fun loadProfile() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProfileActivity).getProfile()
                if (response.isSuccessful) {
                    val user = response.body()
                    binding.etName.setText(user?.namaLengkap)
                    binding.tvNim.text = "NIM: ${user?.nim}"
                    binding.etEmail.setText(user?.email)
                }
            } catch (e: Exception) { }
        }
    }

    private fun updateProfile(name: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProfileActivity)
                    .updateProfile(UpdateProfileRequest(name))
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "✅ Profil Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ProfileActivity, "Gagal Update", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performChangePassword(old: String, new: String) {
        lifecycleScope.launch {
            try {
                val request = ChangePasswordRequest(old, new)
                val response = ApiClient.getApiService(this@ProfileActivity).changePassword(request)
                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "✅ Password Berhasil Diganti!", Toast.LENGTH_SHORT).show()
                    binding.etOldPassword.text?.clear()
                    binding.etNewPassword.text?.clear()
                } else {
                    // Tampilkan pesan error dari server (misal: Password lama salah)
                    val errorMsg = response.errorBody()?.string() ?: "Gagal mengganti password"
                    Toast.makeText(this@ProfileActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performDeleteAccount() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.getApiService(this@ProfileActivity).deleteAccount()

                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "✅ Akun Berhasil Dihapus", Toast.LENGTH_LONG).show()
                    sessionManager.clearSession()

                    val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // INI PENTING: Mengambil pesan error asli dari Backend
                    // Backend akan bilang: "Gagal hapus akun. Pastikan semua data pengajuan sudah tidak ada."
                    val errorMsg = response.errorBody()?.string() ?: "Gagal menghapus akun"
                    Toast.makeText(this@ProfileActivity, errorMsg, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}