package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view.DaftarPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel.LoginPetaniViewModel

class LoginPetaniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPetaniBinding
    private lateinit var viewModel: LoginPetaniViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickAbleText = binding.tvToPenjual
        val fullText = getString(R.string.tv_toPenjual)
        clickAbleText.setClickableText(fullText,2)

        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, DaftarPetaniActivity::class.java)
            startActivity(intent)
        }
    }
}