package com.example.harvest_savior_mobile_dev.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.data.response.ObatListItem

class RekomendasiObatAdapter(private val itemList: List<Obat>) : RecyclerView.Adapter<RekomendasiObatAdapter.ObatViewHolder>() {
    inner class ObatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title_rekomen)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription_rekomen)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice_rekomen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rekomendasi_obat, parent, false)
        return ObatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvTitle.text = item.title
        holder.tvDescription.text = item.description
        holder.tvPrice.text = item.price
    }
}