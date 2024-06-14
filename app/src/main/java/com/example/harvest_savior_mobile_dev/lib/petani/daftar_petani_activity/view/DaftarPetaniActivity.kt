package com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.RegisterViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.google.android.material.snackbar.Snackbar

class DaftarPetaniActivity : AppCompatActivity() {
    private var _binding: ActivityDaftarPetaniBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : DaftarPetaniViewModel
    private lateinit var userFarmerRepository: UserFarmerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDaftarPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        userFarmerRepository = UserFarmerRepository(apiService)


        val daftarViewModelFactory = RegisterViewModelFactory(userFarmerRepository)
        viewModel = ViewModelProvider(this, daftarViewModelFactory).get(DaftarPetaniViewModel::class.java)

        binding.btnRegisterPetani.setOnClickListener {
            val namaLengkap = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val pass = binding.etPw.text.toString()
            val confirmPass = binding.etConfirmPw.text.toString()

            if (namaLengkap.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (isValidEmail(email)) {
                    binding.etEmail.error = null
                    if (pass.length >= 8) {
                        binding.etEmail.error = null
                        if (pass == confirmPass) {
                            binding.etConfirmPw.error = null
                            viewModel.registerFarmer(namaLengkap,email,pass)
                        } else {
                            binding.etConfirmPw.error = "Cek kembali konfirmasi password anda"
                        }
                    } else {
                        binding.etPw.error = "Password harus lebih dari 8 karakter"
                    }
                } else {
                    binding.etEmail.error = "Input email tidak valid"
                }
            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
            }

        }

        viewModel.loading.observe(this) {
            showLoading(it)
        }

        viewModel.registerResult.observe(this) {
            it.onSuccess {
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginPetaniActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }.onFailure {
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressDaftarPetani.visibility = View.VISIBLE
        } else {
            binding.progressDaftarPetani.visibility = View.GONE
        }
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