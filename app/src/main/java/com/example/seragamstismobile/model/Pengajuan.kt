package com.example.seragamstismobile.model

data class Pengajuan(
    val id: Long,
    val tipeSeragam: String,
    val status: String,
    val ukuranBaju: String,
    val ukuranCelanaAtauRok: String,
    val tanggalPengajuan: String
)