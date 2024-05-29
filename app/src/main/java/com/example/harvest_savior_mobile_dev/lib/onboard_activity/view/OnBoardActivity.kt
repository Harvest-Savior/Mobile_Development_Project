package com.example.harvest_savior_mobile_dev.lib.onboard_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityOnBoardBinding
import com.example.harvest_savior_mobile_dev.lib.onboard_activity.viewmodel.OnBoardViewModel

class OnBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardBinding
    private lateinit var viewModel: OnBoardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}