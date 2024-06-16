package com.example.harvest_savior_mobile_dev.util.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.DataItemGetObat
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.view.EditObatActivity
import com.example.harvest_savior_mobile_dev.lib.petani.detail_obat_activity.view.DetailObatActivity
import com.google.android.material.card.MaterialCardView

class ProdukObatPenjualAdapter(private val obat: List<DataItemGetObat?>, private val context: Context) : RecyclerView.Adapter<ProdukObatPenjualAdapter.ProdukObatViewHolder>() {
    inner class ProdukObatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_nama_obat)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_deskripsi_obat)
        val tvStok: TextView = itemView.findViewById(R.id.tv_nilai_stok)
        val btnUbah : Button = itemView.findViewById(R.id.btn_ubah_obat)
        val btnHapus : MaterialCardView = itemView.findViewById(R.id.btn_hapus_obat)
        private val ivContent : ImageView = itemView.findViewById(R.id.iv_produk_obat)

        fun bind(obat: DataItemGetObat) {
            Glide.with(context).load(obat.gambar).fitCenter().into(ivContent)
            tvTitle.text = obat.namaObat
            tvDescription.text = obat.deskripsi
            tvStok.text = obat.harga.toString()

            btnUbah.setOnClickListener {
                val intent = Intent(context, EditObatActivity::class.java).apply {
                    putExtra(TAG_NAMA_OBAT, tvTitle.text.toString())
                }

                itemView.context.startActivity(intent)
            }

            btnHapus.setOnClickListener {

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukObatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produk_obat, parent, false)
        return ProdukObatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return obat.size
    }

    override fun onBindViewHolder(holder: ProdukObatViewHolder, position: Int) {
        val item = obat[position]
        holder.bind(item!!)
    }

    companion object {
        private const val TAG_NAMA_OBAT = "namaObat"
        private const val TAG_PHOTO_OBAT = "photoObat"
        private const val TAG_DESC = "deskripsiObat"
        private const val TAG_STOK = "stokObat"
        private const val TAG_HARGA = "hargaObat"
    }
}