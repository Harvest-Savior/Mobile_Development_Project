package com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityTambahObatBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.viewmodel.TambahObatViewModel

class TambahObatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahObatBinding
    private lateinit var viewModel: TambahObatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahObatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}