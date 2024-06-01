package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view.LoginPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view.DaftarPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.DashboardPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel.LoginPetaniViewModel
import com.example.harvest_savior_mobile_dev.util.AnimationUtil

class LoginPetaniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPetaniBinding
    private lateinit var viewModel: LoginPetaniViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickAbleText = binding.tvToPenjual
        val fullText = getString(R.string.tv_toPenjual)
        clickAbleText.setClickableText(fullText,2,LoginPenjualActivity::class.java)

        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, DaftarPetaniActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }

        binding.btnLoginPetani.setOnClickListener {
            val intent = Intent(this, DashboardPetaniActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }
    }
}