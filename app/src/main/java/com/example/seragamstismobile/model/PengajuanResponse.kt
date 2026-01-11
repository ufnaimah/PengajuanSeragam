package com.example.seragamstismobile.model

data class PengajuanResponse(
    val id: Long,
    val tipeSeragam: String?,
    val status: String?,
    val ukuranBaju: String?,
    val ukuranCelanaAtauRok: String?,
    val ukuranKerudung: String?,
    val tanggalPengajuan: String?,
    val namaLengkap: String?,
    val nim: String?
)