package com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPenjualBinding
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.viewmodel.LoginPenjualViewModel

class LoginPenjualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPenjualBinding
    private lateinit var viewModel: LoginPenjualViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}