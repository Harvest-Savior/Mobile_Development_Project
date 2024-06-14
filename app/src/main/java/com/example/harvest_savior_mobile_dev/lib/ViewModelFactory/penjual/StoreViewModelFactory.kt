package com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.viewmodel.DaftarPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository

class StoreViewModelFactory(private val medicineStoreRepository: MedicineStoreRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DaftarPenjualViewModel::class.java)) {
            return DaftarPenjualViewModel(medicineStoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }
}