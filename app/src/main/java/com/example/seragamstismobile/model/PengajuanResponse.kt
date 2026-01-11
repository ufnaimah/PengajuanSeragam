package com.example.seragamstismobile.model

import java.util.Date

data class PengajuanResponse(
    val id: Long,
    val tipeSeragam: String,
    val status: String,
    val ukuranBaju: String,
    val ukuranCelanaAtauRok: String,
    val ukuranKerudung: String?,
    val tanggalPengajuan: Date,
    val namaLengkap: String,
    val nim: String
)