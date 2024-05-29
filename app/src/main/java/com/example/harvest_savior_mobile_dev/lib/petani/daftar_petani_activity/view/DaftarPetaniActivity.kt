package com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel

class DaftarPetaniActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarPetaniBinding
    private lateinit var viewModel : DaftarPetaniViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}