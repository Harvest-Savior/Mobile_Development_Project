package com.example.harvest_savior_mobile_dev.lib.penjual.setting_activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivitySettingPenjualBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view.LoginPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.setting_activity.viewModel.SettingPenjualViewModel
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.datastoreStore

class SettingPenjualActivity : AppCompatActivity() {

    private var _binding : ActivitySettingPenjualBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : SettingPenjualViewModel
    private lateinit var medicineStoreRepository: MedicineStoreRepository
    private var namToko : String? = null
    private var emailToko : String? = null
    private var imgProfil : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig.getApiServiceDeteksi()
        val pref = LoginStorePreference(application.datastoreStore)
        medicineStoreRepository = MedicineStoreRepository(apiService,apiService2,pref)

        namToko = intent.getStringExtra("namaToko")
        emailToko = intent.getStringExtra("email")
        imgProfil = intent.getStringExtra("gambar")

        viewModel = ViewModelProvider(this, LoginStoreVMFactory(medicineStoreRepository,pref)).get(SettingPenjualViewModel::class.java)

        binding.btnKeluarPenjual.setOnClickListener {
            viewModel.logout()
        }

        if (!imgProfil.isNullOrEmpty()) {
            loadProfileImage(imgProfil!!)
        } else {
            // Jika gambar dari intent null, coba ambil dari preference
            viewModel.getGambar().observe(this) { gambarDariPreference ->
                gambarDariPreference?.let {
                    loadProfileImage(it)
                }
            }
        }

        binding.tvNamaToko.text = namToko
        binding.tvEmailToko.text = emailToko

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
        val intent = Intent(this, LoginPenjualActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        AnimationUtil.startActivityWithSlideAnimation(this, intent)
    }

    private fun loadProfileImage(url: String) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.profile_2)
            .error(R.drawable.image_3_gray400)
            .into(binding.ivProfilePetani)
    }

    companion object {
        private const val TAG = "SettingPenjualActivity"

    }
}