package com.example.seragamstismobile.ui.pengajuan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seragamstismobile.databinding.ItemPengajuanBinding
import com.example.seragamstismobile.model.PengajuanResponse
import java.text.SimpleDateFormat
import java.util.Locale

class PengajuanAdapter(private val list: List<PengajuanResponse>) :
    RecyclerView.Adapter<PengajuanAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPengajuanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPengajuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.apply {
            tvTipeSeragam.text = "Tipe: ${data.tipeSeragam}"
            tvStatus.text = data.status
            tvUkuran.text = "Baju: ${data.ukuranBaju} | Celana: ${data.ukuranCelanaAtauRok}"

            // Format Tanggal sesuai response dari backend
            val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            tvTanggal.text = "Diajukan pada: ${format.format(data.tanggalPengajuan)}"
        }
    }

    override fun getItemCount(): Int = list.size
}