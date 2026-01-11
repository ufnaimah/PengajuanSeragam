package com.example.seragamstismobile.ui.pengajuan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seragamstismobile.databinding.ItemPengajuanBinding
import com.example.seragamstismobile.model.PengajuanResponse

class PengajuanAdapter(private val list: List<PengajuanResponse>) :
    RecyclerView.Adapter<PengajuanAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPengajuanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPengajuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.apply {
            tvTipe.text = "Seragam: ${item.tipeSeragam ?: "-"}"
            tvStatus.text = item.status ?: "MENUNGGU"

            val detail = StringBuilder()
            detail.append("Baju: ${item.ukuranBaju ?: "-"}")
            detail.append(", Celana/Rok: ${item.ukuranCelanaAtauRok ?: "-"}")

            if (!item.ukuranKerudung.isNullOrEmpty()) {
                detail.append(", Kerudung: ${item.ukuranKerudung}")
            }
            tvDetail.text = detail.toString()
        }
    }

    override fun getItemCount() = list.size
}