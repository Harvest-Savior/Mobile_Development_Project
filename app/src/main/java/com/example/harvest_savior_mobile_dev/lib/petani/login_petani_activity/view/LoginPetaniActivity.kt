package com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiService
import com.example.harvest_savior_mobile_dev.databinding.ActivityLoginPetaniBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.LoginViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view.LoginPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.view.DaftarPetaniActivity
import com.example.harvest_savior_mobile_dev.lib.petani.dashboard_petani_activity.view.DashboardPetaniActivity
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

    private lateinit var userFarmerRepository: UserFarmerRepository
    private lateinit var pref : LoginPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginPetaniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        userFarmerRepository = UserFarmerRepository(apiService)
        pref =LoginPreference.getInstance(application.datastore)

        val loginViewModelFactory = LoginViewModelFactory(userFarmerRepository,pref)
        viewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginPetaniViewModel::class.java)

        val clickAbleText = binding.tvToPenjual
        val fullText = getString(R.string.tv_toPenjual)
        clickAbleText.setClickableText(fullText,2,LoginPenjualActivity::class.java)

        viewModel.loginResult.observe(this) {response ->
            response.onSuccess {
                val intent = Intent(this, DashboardPetaniActivity::class.java)
//                intent.putExtra("response",response)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                AnimationUtil.startActivityWithSlideAnimation(this, intent)
//                viewModel.saveLoginSession(response)
            }.onFailure {


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
                    viewModel.login(inputEmail,inputPass)
                } else {
                    binding.etEmail.error = "Input email tidak valid"
                }

            } else {
                Snackbar.make(window.decorView.rootView, R.string.error_regist_allnull, Snackbar.LENGTH_SHORT).show()
            }
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
}