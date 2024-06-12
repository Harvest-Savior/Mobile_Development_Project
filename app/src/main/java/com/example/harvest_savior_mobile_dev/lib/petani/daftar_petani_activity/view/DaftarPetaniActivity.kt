package com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.util.AnimationUtil

class DaftarPetaniActivity : AppCompatActivity() {
    private var _binding: ActivityDaftarPetaniBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : DaftarPetaniViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDaftarPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onBackPressed() {
        super.onBackPressed()
        AnimationUtil.finishActivityWithSlideAnimation(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}