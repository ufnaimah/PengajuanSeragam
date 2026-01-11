package com.example.seragamstismobile.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seragamstismobile.databinding.ActivityHomeBinding
import com.example.seragamstismobile.ui.login.LoginActivity
import com.example.seragamstismobile.util.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            // Ganti jadi clearSession()
            SessionManager(this).clearSession()
            val intent = Intent(this, LoginActivity::class.java)
            // Tambahkan flag agar tidak bisa back ke Home setelah logout
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}