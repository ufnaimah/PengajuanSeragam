package com.example.seragamstismobile.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityLoginBinding
import com.example.seragamstismobile.model.LoginRequest
import com.example.seragamstismobile.ui.home.HomeActivity
import com.example.seragamstismobile.util.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Cek jika sudah login, langsung ke Home
        if (sessionManager.fetchAuthToken() != null) {
            moveToHome()
        }

        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvRegister.setOnClickListener {
            // Navigasi ke RegisterActivity
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        // Tampilkan loading (opsional jika ada ProgressBar di XML)
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Gunakan ApiClient.instance sesuai file ApiClient.kt kamu
                val response = ApiClient.instance.login(LoginRequest(username, password))

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.token != null) {
                        sessionManager.saveToken(loginResponse.token)
                        Toast.makeText(this@LoginActivity, "Selamat Datang, ${loginResponse.username}", Toast.LENGTH_SHORT).show()
                        moveToHome()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login Gagal: Periksa kembali akun Anda", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Koneksi Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}