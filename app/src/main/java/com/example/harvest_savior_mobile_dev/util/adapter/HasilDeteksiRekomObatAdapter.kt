package com.example.harvest_savior_mobile_dev.util.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.lib.petani.detail_obat_activity.view.DetailObatActivity

class HasilDeteksiRekomObatAdapter(private val itemList: List<Obat>, private val context: Context) : RecyclerView.Adapter<HasilDeteksiRekomObatAdapter.ObatViewHolder>() {

    inner class ObatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_rekomen)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription_rekomen)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice_rekomen)

        fun bind(obat:Obat) {
            tvTitle.text = obat.title
            tvDescription.text = obat.description
            tvPrice.text = obat.price

            itemView.setOnClickListener {
                val intent = Intent(context, DetailObatActivity::class.java).apply {
                    putExtra(TAG_NAMA_OBAT, tvTitle.text.toString())
                }

                itemView.context.startActivity(intent)
            }

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HasilDeteksiRekomObatAdapter.ObatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rekomendasi_obat, parent, false)
        return ObatViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HasilDeteksiRekomObatAdapter.ObatViewHolder,
        position: Int
    ) {
        val item = itemList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    companion object {
        private const val TAG_NAMA_OBAT = "namaObat"
        private const val TAG_PHOTO_OBAT = "photoObat"
        private const val TAG_DESC = "deskripsiObat"
        private const val TAG_STOK = "stokObat"
        private const val TAG_HARGA = "hargaObat"
    }
}