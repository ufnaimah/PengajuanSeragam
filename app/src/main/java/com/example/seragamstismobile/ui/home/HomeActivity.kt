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
            SessionManager(this).clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
