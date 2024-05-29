package com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPenjualBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.viewmodel.DaftarPenjualViewModel

class DaftarPenjualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarPenjualBinding
    private lateinit var viewModel: DaftarPenjualViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}