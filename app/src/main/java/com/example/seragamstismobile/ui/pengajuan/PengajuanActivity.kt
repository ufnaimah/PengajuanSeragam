package com.example.seragamstismobile.ui.pengajuan

import android.os.Bundle
import android.view.View
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

        // Setup Dropdown Tipe Seragam (PDA/PDO)
        val tipeSeragam = arrayOf("PDA", "PDO")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tipeSeragam)
        binding.actvTipeSeragam.setAdapter(adapter)

        binding.btnKirimPengajuan.setOnClickListener { kirimData() }
    }

    private fun kirimData() {
        // Ambil data dari input
        val tipe = binding.actvTipeSeragam.text.toString().trim()
        val baju = binding.etUkuranBaju.text.toString().trim()
        val celana = binding.etUkuranCelana.text.toString().trim()
        val kerudung = binding.etUkuranKerudung.text.toString().trim()

        // Validasi
        if (tipe.isEmpty() || baju.isEmpty() || celana.isEmpty()) {
            Toast.makeText(this, "Tipe dan Ukuran (Baju & Celana) wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat Request
        val request = PengajuanRequest(
            tipeSeragam = tipe,
            ukuranBaju = baju,
            ukuranCelanaAtauRok = celana,
            ukuranKerudung = if (kerudung.isEmpty()) null else kerudung
        )

        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // PENTING: Gunakan 'this@PengajuanActivity' sebagai context agar Token terbawa!
                val response = ApiClient.getApiService(this@PengajuanActivity).createPengajuan(request)

                if (response.isSuccessful) {
                    Toast.makeText(this@PengajuanActivity, "Pengajuan Berhasil Dikirim!", Toast.LENGTH_LONG).show()
                    finish() // Kembali ke Home
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Gagal mengirim"
                    Toast.makeText(this@PengajuanActivity, "Gagal: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PengajuanActivity, "Koneksi Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}