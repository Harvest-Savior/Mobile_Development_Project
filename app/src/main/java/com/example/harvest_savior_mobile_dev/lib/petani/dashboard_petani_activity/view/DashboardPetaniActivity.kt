package com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityDashboardPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.viewmodel.DashboardPetaniViewModel

class DashboardPetaniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardPetaniBinding
    private lateinit var viewModel: DashboardPetaniViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}