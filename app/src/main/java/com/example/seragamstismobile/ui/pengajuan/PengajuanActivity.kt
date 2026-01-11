package com.example.seragamstismobile.ui.pengajuan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seragamstismobile.databinding.ActivityPengajuanBinding

class PengajuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengajuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKirim.setOnClickListener {
            val tipeSeragam = binding.spinnerTipeSeragam.selectedItem.toString()
            val ukuranBaju = binding.etUkuranBaju.text.toString()

            Toast.makeText(
                this,
                "Pengajuan: $tipeSeragam, Ukuran $ukuranBaju",
                Toast.LENGTH_SHORT
            ).show()

            // API call nanti bisa ditambahkan di sini
        }
    }
}
