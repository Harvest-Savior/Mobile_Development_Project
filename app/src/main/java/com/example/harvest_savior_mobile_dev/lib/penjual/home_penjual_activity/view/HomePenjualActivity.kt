package com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.databinding.ActivityHomePenjualBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.viewmodel.HomePenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view.TambahObatActivity
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.adapter.ProdukObatPenjualAdapter
import com.example.harvest_savior_mobile_dev.util.adapter.RekomendasiObatAdapter

class HomePenjualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePenjualBinding
    private lateinit var viewModel: HomePenjualViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var produkObatAdapter: ProdukObatPenjualAdapter
    private val rekomendasiObatList = listOf(
        Obat("Fungisida", "Untuk mengendalikan penyakit, seperti NOPAT...", "Rp5.500"),
        Obat("Insektisida", "Untuk mengendalikan hama, seperti Pounce, B...", "Rp6.500")

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvProdukObat
        recyclerView.layoutManager = LinearLayoutManager(this)
        produkObatAdapter = ProdukObatPenjualAdapter(rekomendasiObatList, this)
        recyclerView.adapter = produkObatAdapter

        binding.btnAddProduk.setOnClickListener {
            val intent = Intent(this, TambahObatActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }
    }
}