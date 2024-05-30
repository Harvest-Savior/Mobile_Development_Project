package com.example.harvest_savior_mobile_dev.lib.petani.hasil_deteksi_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityHasilDeteksiBinding
import com.example.harvest_savior_mobile_dev.lib.petani.hasil_deteksi_activity.viewModel.HasilDeteksiViewModel

class HasilDeteksiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHasilDeteksiBinding
    private lateinit var viewModel : HasilDeteksiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilDeteksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}