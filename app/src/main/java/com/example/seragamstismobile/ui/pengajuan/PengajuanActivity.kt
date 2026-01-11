package com.example.seragamstismobile.ui.pengajuan

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seragamstismobile.api.ApiClient
import com.example.seragamstismobile.databinding.ActivityPengajuanBinding
import com.example.seragamstismobile.model.PengajuanRequest
import kotlinx.coroutines.launch

class PengajuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengajuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Dropdown Tipe Seragam sesuai DTO
        val tipeSeragam = arrayOf("PDA", "PDO")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipeSeragam)
        binding.actvTipeSeragam.setAdapter(adapter)

        binding.btnKirimPengajuan.setOnClickListener { performCreatePengajuan() }
    }

    private fun performCreatePengajuan() {
        val tipe = binding.actvTipeSeragam.text.toString()
        val baju = binding.etUkuranBaju.text.toString().trim()
        val celana = binding.etUkuranCelana.text.toString().trim()
        val kerudung = binding.etUkuranKerudung.text.toString().trim()

        if (tipe.isEmpty() || baju.isEmpty() || celana.isEmpty()) {
            Toast.makeText(this, "Tipe dan Ukuran (Baju & Celana/Rok) wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val request = PengajuanRequest(
            tipeSeragam = tipe,
            ukuranBaju = baju,
            ukuranCelanaAtauRok = celana,
            ukuranKerudung = if (kerudung.isEmpty()) null else kerudung
        )

        lifecycleScope.launch {
            try {
                // Pastikan ApiClient sudah mengirimkan token JWT di header
                val response = ApiClient.getApiService(this@PengajuanActivity).createPengajuan(request)
                if (response.isSuccessful) {
                    Toast.makeText(this@PengajuanActivity, "Pengajuan berhasil dikirim!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@PengajuanActivity, "Gagal mengirim pengajuan", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PengajuanActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}