package com.example.harvest_savior_mobile_dev.lib.petani.setting_activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivitySettingPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.LoginViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.setting_activity.viewModel.SettingPetaniViewModel
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.example.harvest_savior_mobile_dev.util.datastore

class SettingPetaniActivity : AppCompatActivity() {

    private var _binding : ActivitySettingPetaniBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : SettingPetaniViewModel
    private lateinit var userFarmerRepository: UserFarmerRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        userFarmerRepository = UserFarmerRepository(apiService)

        val pref =LoginPreference.getInstance(application.datastore)
        viewModel =ViewModelProvider(this, LoginViewModelFactory(userFarmerRepository,pref)).get(
            SettingPetaniViewModel::class.java
        )

        binding.btnKeluarPetani.setOnClickListener {
            viewModel.logout()
        }

        viewModel.getLoginSession().observe(this) { token ->
            if (token == null) {
                navigateToLogin()
                Log.d(TAG, "token-setting : $token")
            } else {
                Log.d(TAG, "token-setting : $token")
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginPetaniActivity::class.java)
        AnimationUtil.startActivityWithSlideAnimation(this, intent)
        finish()
    }

    companion object {
        private const val TAG = "SettingPetaniActivity"
    }
}