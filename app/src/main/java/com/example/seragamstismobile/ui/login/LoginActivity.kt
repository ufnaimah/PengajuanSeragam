package com.example.seragamstismobile.ui.login

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            lifecycleScope.launch {
                try {
                    val response = ApiClient.getApiService(this@LoginActivity).login(LoginRequest(username, password))
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        if (token != null) {
                            SessionManager(this@LoginActivity).saveToken(token)
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Gagal! Cek kredensial.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Error Koneksi: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}