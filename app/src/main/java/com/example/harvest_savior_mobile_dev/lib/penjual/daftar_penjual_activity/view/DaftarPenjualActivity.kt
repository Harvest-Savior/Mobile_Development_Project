package com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPenjualBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.StoreViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.RegisterViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.viewmodel.DaftarPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view.LoginPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.datastore
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import com.google.android.material.snackbar.Snackbar

class DaftarPenjualActivity : AppCompatActivity() {

    private  var _binding: ActivityDaftarPenjualBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DaftarPenjualViewModel
    private lateinit var medicineStoreRepository: MedicineStoreRepository
    private lateinit var pref : LoginStorePreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDaftarPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = LoginStorePreference.getInstance(application.datastoreStore)
        val apiService = ApiConfig.getApiService()
        medicineStoreRepository = MedicineStoreRepository(apiService,pref)


        val daftarViewModelFactory = StoreViewModelFactory(medicineStoreRepository)
        viewModel = ViewModelProvider(this, daftarViewModelFactory).get(DaftarPenjualViewModel::class.java)

        binding.btnRegisterPenjual.setOnClickListener {
            val namaToko = binding.etNamaToko.text.toString()
            val alamat = binding.etAlamatPenjual.text.toString()
            val email = binding.etEmail.text.toString()
            val noHp = binding.etNomorPenjual.text.toString()
            val pass = binding.etPwPenjual.text.toString()
            val confirmPass = binding.etConfirmPwPenjual.text.toString()

            if (namaToko.isNotEmpty() && alamat.isNotEmpty() && email.isNotEmpty() && noHp.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (isValidEmail(email)) {
                    binding.etEmail.error = null
                    if (pass.length >= 8) {
                        binding.etEmail.error = null
                        if (pass == confirmPass) {
                            binding.etConfirmPwPenjual.error = null
                            viewModel.registerStore(namaToko,alamat,email,noHp.toInt(),pass)
                        } else {
                            binding.etConfirmPwPenjual.error = "Cek kembali konfirmasi password anda"
                        }
                    } else {
                        binding.etPwPenjual.error = "Password harus lebih dari 8 karakter"
                    }
                } else {
                    binding.etEmail.error = "Input email tidak valid"
                }
            } else {
                Snackbar.make(
                    window.decorView.rootView,
                    R.string.error_regist_allnull,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.loading.observe(this) {
            showLoading(it)
        }

        viewModel.registerResult.observe(this) {
            it.onSuccess {
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginPenjualActivity::class.java)
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
            binding.progressDaftarPenjual.visibility = View.VISIBLE
        } else {
            binding.progressDaftarPenjual.visibility = View.GONE
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