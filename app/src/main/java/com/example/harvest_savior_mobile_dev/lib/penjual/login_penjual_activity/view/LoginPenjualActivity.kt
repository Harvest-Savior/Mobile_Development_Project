package com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPenjualBinding
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.view.DaftarPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.viewmodel.LoginPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.util.AnimationUtil

class LoginPenjualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPenjualBinding
    private lateinit var viewModel: LoginPenjualViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickAbleText = binding.tvToPetani
        val fullText = getString(R.string.klik_disini_untuk_login_sebagai_petani)
        clickAbleText.setClickableText(fullText,2,LoginPetaniActivity::class.java)

        binding.tvDaftarPenjual.setOnClickListener {
            val intent = Intent(this, DaftarPenjualActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }
    }
}