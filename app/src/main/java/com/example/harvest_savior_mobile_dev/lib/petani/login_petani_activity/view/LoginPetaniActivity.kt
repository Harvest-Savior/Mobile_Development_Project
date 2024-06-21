package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.LoginFarmerResponse
import com.example.harvest_savior_mobile_dev.data.response.LoginStoreResponse
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.LoginViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view.HomePenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view.LoginPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view.DaftarPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.DashboardPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel.LoginJsonPetaniVM
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.viewmodel.LoginPetaniViewModel
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginPreference
import com.example.harvest_savior_mobile_dev.util.datastore
import com.google.android.material.snackbar.Snackbar

class LoginPetaniActivity : AppCompatActivity() {

    private var _binding: ActivityLoginPetaniBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginPetaniViewModel
    private lateinit var viewModel2: LoginJsonPetaniVM

    private lateinit var userFarmerRepository: UserFarmerRepository
    private lateinit var pref : LoginPreference

    private var tokenDeteksi :String ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig.getApiServiceDeteksi()

        userFarmerRepository = UserFarmerRepository(apiService,apiService2)
        pref =LoginPreference.getInstance(application.datastore)

        val loginViewModelFactory = LoginViewModelFactory(userFarmerRepository,pref)
        viewModel2 = ViewModelProvider(this, loginViewModelFactory).get(LoginJsonPetaniVM::class.java)

        val clickAbleText = binding.tvToPenjual
        val fullText = getString(R.string.tv_toPenjual)
        clickAbleText.setClickableText(fullText,2,LoginPenjualActivity::class.java)

        viewModel2.loginResult.observe(this) {
            it.onSuccess { response ->
                response.data?.let { data ->
                    viewModel2.getTokenDeteksi().observe(this) { tokenDeteksi ->
                        val intent = Intent(this, DashboardPetaniActivity::class.java).apply {
                            putExtra("token", data.accessToken)
                            putExtra("namaToko", data.namaLengkap)
                            putExtra("email", data.email)
                            putExtra(TOKEN_DETEKSI, tokenDeteksi)
                        }
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        AnimationUtil.startActivityWithSlideAnimation(this, intent)
                        finish()
                    }
                    viewModel2.saveLoginSession(response)
                    Snackbar.make(window.decorView.rootView, "Berhasil Login", Snackbar.LENGTH_SHORT).show()
                }
            }.onFailure {
                Snackbar.make(window.decorView.rootView, "Gagal Login", Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel2.getTokenResult.observe(this) {
            it.onSuccess { response ->
                viewModel2.saveTokenDeteksi(response.accessToken!!)
            }
        }



        viewModel2.getLoginSession().observe(this) { isLoged : LoginFarmerResponse? ->
            if (isLoged != null){
                val intent = Intent(this, DashboardPetaniActivity::class.java).apply {
                    putExtra("token", isLoged.data?.accessToken)
                    putExtra("namaToko", isLoged.data?.namaLengkap)
                    putExtra("email", isLoged.data?.email)
                }
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                AnimationUtil.startActivityWithSlideAnimation(this, intent)
            }
        }

        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this, DaftarPetaniActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }

        binding.btnLoginPetani.setOnClickListener {
            val inputEmail = binding.etEmail.text.toString()
            val inputPass = binding.etPw.text.toString()

            if (inputEmail.isNotEmpty() && inputPass.isNotEmpty()) {
                if (isValidEmail(inputEmail)) {
                    viewModel2.loginAndFetchToken(inputEmail,inputPass)
                } else {
                    binding.etEmail.error = "Input email tidak valid"
                }

            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel2.loading.observe(this) {
            showLoading(it)
        }
    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showLoading(a: Boolean) {
        if (a) {
            binding.progressLoginPetani.visibility = View.VISIBLE
        } else {
            binding.progressLoginPetani.visibility = View.GONE
        }
    }

    companion object {
        private const val TOKEN_DETEKSI = "tokenDeteksi"
    }
}