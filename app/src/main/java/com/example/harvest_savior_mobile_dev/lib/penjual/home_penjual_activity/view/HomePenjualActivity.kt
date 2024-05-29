package com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityHomePenjualBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.viewmodel.HomePenjualViewModel

class HomePenjualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePenjualBinding
    private lateinit var viewModel: HomePenjualViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}