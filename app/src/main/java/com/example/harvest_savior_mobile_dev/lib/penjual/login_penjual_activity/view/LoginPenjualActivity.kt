package com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPenjualBinding
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.view.DaftarPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view.HomePenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.viewmodel.LoginPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginPenjualActivity : AppCompatActivity() {
    private var _binding: ActivityLoginPenjualBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginPenjualViewModel
    private lateinit var medicineStoreRepository: MedicineStoreRepository
    private lateinit var pref : LoginStorePreference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        _binding = ActivityLoginPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickAbleText = binding.tvToPetani
        val fullText = getString(R.string.klik_disini_untuk_login_sebagai_petani)
        clickAbleText.setClickableText(fullText,2,LoginPetaniActivity::class.java)

        val apiService = ApiConfig.getApiService()
        pref = LoginStorePreference.getInstance(application.datastoreStore)
        medicineStoreRepository = MedicineStoreRepository(apiService,pref)

        val loginViewModelFactory = LoginStoreVMFactory(medicineStoreRepository,pref)
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginPenjualViewModel::class.java)



        binding.btnLoginPenjual.setOnClickListener {
            val inputEmail = binding.etEmailPenjual.text.toString()
            val inputPass = binding.etPwPenjual.text.toString()

            if (inputEmail.isNotEmpty() && inputPass.isNotEmpty()) {
                if (isValidEmail(inputEmail)) {
                    viewModel.login(inputEmail,inputPass)
                } else {
                    binding.etEmailPenjual.error = "Input email tidak valid"
                }
            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.tvDaftarPenjual.setOnClickListener {
            val intent = Intent(this, DaftarPenjualActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }

        viewModel.loginResult.observe(this) {
            it.onSuccess { response ->
                response.data?.let { data ->
                    val intent = Intent(this, HomePenjualActivity::class.java).apply {
                        putExtra("token", data.accessToken)
                        putExtra("namaToko", data.namaToko)
                        putExtra("email", data.email)
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    AnimationUtil.startActivityWithSlideAnimation(this, intent)
                    finish()
                    viewModel.saveLoginSession(response)
                    Snackbar.make(window.decorView.rootView, "Berhasil Login", Snackbar.LENGTH_SHORT).show()
                }
            }.onFailure {
                Snackbar.make(window.decorView.rootView, "Gagal Login", Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.getLoginSession().observe(this) { isLoged : LoginStoreResponse? ->
            if (isLoged != null){
                val intent = Intent(this, HomePenjualActivity::class.java).apply {
                    putExtra("token", isLoged.data?.accessToken)
                    putExtra("namaToko", isLoged.data?.namaToko)
                    putExtra("email", isLoged.data?.email)
                }
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                AnimationUtil.startActivityWithSlideAnimation(this, intent)
            }
        }

        viewModel.loading.observe(this) {
            showLoading(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressLoginPenjual.visibility = View.VISIBLE
        } else {
            binding.progressLoginPenjual.visibility = View.GONE
        }
    }
}