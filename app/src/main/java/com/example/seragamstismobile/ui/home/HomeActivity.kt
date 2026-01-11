package com.example.seragamstismobile.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seragamstismobile.databinding.ActivityHomeBinding
import com.example.seragamstismobile.ui.login.LoginActivity
import com.example.seragamstismobile.ui.pengajuan.PengajuanActivity
import com.example.seragamstismobile.ui.pengajuan.PengajuanHistoryActivity
import com.example.seragamstismobile.ui.profile.ProfileActivity
import com.example.seragamstismobile.util.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menu 1: Ke Halaman Pengajuan
        binding.cardAjukan.setOnClickListener {
            startActivity(Intent(this, PengajuanActivity::class.java))
        }

        // Menu 2: Ke Halaman Riwayat (Pastikan kamu sudah membuat Activity ini)
        binding.cardRiwayat.setOnClickListener {
            startActivity(Intent(this, PengajuanHistoryActivity::class.java))
        }

        // Menu 3: Ke Halaman Profil
        binding.cardProfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Menu 4: Logout
        binding.cardLogout.setOnClickListener {
            SessionManager(this).clearSession()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}