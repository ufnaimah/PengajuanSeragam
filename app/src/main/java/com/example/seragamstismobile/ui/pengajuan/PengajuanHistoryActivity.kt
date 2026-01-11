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

        // Setup RecyclerView
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        loadHistory()
    }

    private fun loadHistory() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Panggil endpoint /api/pengajuan/saya
                val response = ApiClient.getApiService(this@PengajuanHistoryActivity).getMyPengajuan()

                if (response.isSuccessful) {
                    val historyList = response.body() ?: emptyList()
                    if (historyList.isEmpty()) {
                        Toast.makeText(this@PengajuanHistoryActivity, "Belum ada riwayat pengajuan", Toast.LENGTH_SHORT).show()
                    }
                    // Pasang data ke Adapter
                    binding.rvHistory.adapter = PengajuanAdapter(historyList)
                } else {
                    Toast.makeText(this@PengajuanHistoryActivity, "Gagal mengambil riwayat: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PengajuanHistoryActivity, "Koneksi Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}