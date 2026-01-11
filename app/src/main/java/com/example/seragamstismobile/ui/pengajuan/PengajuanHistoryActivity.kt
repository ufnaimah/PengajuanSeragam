package com.example.seragamstismobile.ui.pengajuan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityPengajuanHistoryBinding
import kotlinx.coroutines.launch

class PengajuanHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengajuanHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // PENTING: Set Layout Manager (Tanpa ini list tidak akan muncul)
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        loadData()
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                // Panggil API dengan context 'this' agar Token Login terbawa
                val response = ApiClient.getApiService(this@PengajuanHistoryActivity).getMyPengajuan()

                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()

                    if (list.isEmpty()) {
                        Toast.makeText(this@PengajuanHistoryActivity, "Belum ada riwayat pengajuan", Toast.LENGTH_SHORT).show()
                    }

                    // Pasang Adapter
                    binding.rvHistory.adapter = PengajuanAdapter(list)
                } else {
                    Toast.makeText(this@PengajuanHistoryActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PengajuanHistoryActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}