package com.example.harvest_savior_mobile_dev.lib.petani.detail_obat_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityDetailObatBinding
import com.example.harvest_savior_mobile_dev.lib.petani.detail_obat_activity.viewmodel.DetailObatViewModel

class DetailObatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailObatBinding
    private lateinit var viewModel: DetailObatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_obat)
    }
}