package com.example.seragamstismobile.model

data class PengajuanRequest(
    val tipeSeragam: String,         // Harus "PDA" atau "PDO"
    val ukuranBaju: String,          //
    val ukuranCelanaAtauRok: String, //
    val ukuranKerudung: String?      // Opsional
)