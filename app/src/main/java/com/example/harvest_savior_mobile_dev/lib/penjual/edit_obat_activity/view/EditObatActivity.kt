package com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityEditObatBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.viewmodel.EditObatViewModel

class EditObatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditObatBinding
    private lateinit var viewModel: EditObatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditObatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}