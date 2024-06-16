package com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.Obat
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityHomePenjualBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.viewmodel.HomePenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.setting_activity.activity.SettingPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.setting_activity.viewModel.SettingPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view.TambahObatActivity
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.adapter.ProdukObatPenjualAdapter
import com.example.harvest_savior_mobile_dev.util.adapter.RekomendasiObatAdapter
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import kotlinx.coroutines.launch

class HomePenjualActivity : AppCompatActivity() {

    private var _binding: ActivityHomePenjualBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomePenjualViewModel

    private lateinit var pref : LoginStorePreference
    private lateinit var medicineStoreRepository: MedicineStoreRepository

    private lateinit var recyclerView: RecyclerView
    private lateinit var produkObatAdapter: ProdukObatPenjualAdapter
    private val rekomendasiObatList = listOf(
        Obat("Fungisida", "Untuk mengendalikan penyakit, seperti NOPAT...", "Rp5.500"),
        Obat("Insektisida", "Untuk mengendalikan hama, seperti Pounce, B...", "Rp6.500")

    )

    private var token2 : String? = null
    private var namToko : String? = null
    private var emailToko : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomePenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvProdukObat
        recyclerView.layoutManager = LinearLayoutManager(this)
        produkObatAdapter = ProdukObatPenjualAdapter(rekomendasiObatList, this)
        recyclerView.adapter = produkObatAdapter

        pref = LoginStorePreference.getInstance(application.datastoreStore)
        val apiService = ApiConfig.getApiService()
        medicineStoreRepository = MedicineStoreRepository(apiService,pref)

        val homeViewModelFactory = LoginStoreVMFactory(medicineStoreRepository,pref)
        viewModel = ViewModelProvider(this,homeViewModelFactory).get(HomePenjualViewModel::class.java)
        token2 = intent.getStringExtra("token")
        namToko = intent.getStringExtra("namaToko")
        emailToko = intent.getStringExtra("email")



        Log.d(TAG,"token : $token2")


        binding.tvWelcomeHomePetani.text = "Selamat Datang, $namToko"

        binding.btnAddProduk.setOnClickListener {
            val intent = Intent(this, TambahObatActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }
        binding.ivProfilePetani.setOnClickListener {
            val intent = Intent(this, SettingPenjualActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomePenjualActivity"
    }
}