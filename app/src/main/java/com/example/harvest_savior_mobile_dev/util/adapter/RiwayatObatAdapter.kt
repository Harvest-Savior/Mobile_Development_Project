package com.example.harvest_savior_mobile_dev.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.PredictionsItem

class RiwayatObatAdapter(private val obatList: List<PredictionsItem?>,private val context:Context) : RecyclerView.Adapter<RiwayatObatAdapter.ObatViewHolder>() {
    inner class ObatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaObat: TextView = itemView.findViewById(R.id.tv_info_riwayat_deteksi)
        private val tvTanggal: TextView = itemView.findViewById(R.id.tv_tanggal_riwayat)
        private val tvWarning: TextView = itemView.findViewById(R.id.tv_riwayat_warning)
        private val ivDeteksi: ImageView = itemView.findViewById(R.id.iv_riwayat_deteksi)

        fun bind(obat: PredictionsItem?) {
                tvNamaObat.text = obat?.prediction
                tvTanggal.text = obat?.timestamp
                Glide.with(context).load(obat?.image).placeholder(R.drawable.example_iv_riwayat).error(R.drawable.image_3_gray400).into(ivDeteksi)
                tvWarning.text = "Perlu Tindakan"


            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_petani, parent, false)
        return ObatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return obatList.size
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val obat = obatList[position]
        holder.bind(obat)
    }
}
