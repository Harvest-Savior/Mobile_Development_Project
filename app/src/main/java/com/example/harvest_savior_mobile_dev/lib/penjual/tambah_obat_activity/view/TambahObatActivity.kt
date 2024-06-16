package com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityTambahObatBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view.HomePenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.viewmodel.TambahObatViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.camera_activity.view.CameraPetaniActivity.Companion.CAMERAX_RESULT
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.createCustomTempFile
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import com.example.harvest_savior_mobile_dev.util.getImageUri
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class TambahObatActivity : AppCompatActivity() {
    private var _binding: ActivityTambahObatBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TambahObatViewModel
    private lateinit var image : Uri
    private var currentImageUri: Uri? = null

    private lateinit var pref : LoginStorePreference
    private lateinit var medicineStoreRepository: MedicineStoreRepository

    private var token2 : String? = null
    private var namToko : String? = null
    private var emailToko : String? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTambahObatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val apiService = ApiConfig.getApiService()
        pref = LoginStorePreference.getInstance(application.datastoreStore)
        medicineStoreRepository = MedicineStoreRepository(apiService,pref)

        val tambahViewModelFactory = LoginStoreVMFactory(medicineStoreRepository,pref)
        viewModel = ViewModelProvider(this, tambahViewModelFactory).get(TambahObatViewModel::class.java)
        token2 = intent.getStringExtra("token")
        namToko = intent.getStringExtra("namaToko")
        emailToko = intent.getStringExtra("email")

        binding.ivProduk.setImageURI(image)

        binding.btnTambahObat.setOnClickListener {
            lifecycleScope.launch {

            }
        }
        binding.btnChangeImage.setOnClickListener { startCamera() }
        binding.btnOpenGallery.setOnClickListener { startGallery() }
        binding.ivProduk.setOnClickListener { startCamera() }
    }

    private  suspend fun addStory() {
        val namaObatText = binding.etNamaObatAdd.text.toString()
        val namaObat = namaObatText.toString().toRequestBody("text/plain".toMediaType())

        val descriptionText = binding.etDeskripsiObatAdd.text.toString()
        val description = descriptionText.toString().toRequestBody("text/plain".toMediaType())

        val stokText = binding.etStokAdd.text.toString()
        val stok = stokText.toIntOrNull().toString().toRequestBody("text/plain".toMediaType())

        val hargaText = binding.etHargaObatAdd.text.toString()
        val harga = hargaText.toIntOrNull().toString().toRequestBody("text/plain".toMediaType())

        val img = binding.ivProduk

        val photoFile2 = convertImageViewToFile(img, "photo.jpg")
        if (descriptionText.isEmpty()) {
            Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val compressedPhotoFile = compressImageFile(photoFile2)
        val photoUri = Uri.fromFile(compressedPhotoFile)

        val requestImageFile = compressedPhotoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            compressedPhotoFile.name,
            requestImageFile
        )
        try {


            viewModel.addObat("Bearer $token2",namaObatText,descriptionText,stokText.toInt(),hargaText.toInt(),imageMultipart)

            binding.btnTambahObat.isEnabled = false

            val intent = Intent(this, HomePenjualActivity::class.java)
            token2 = intent.getStringExtra("token")
            namToko = intent.getStringExtra("namaToko")
            emailToko = intent.getStringExtra("email")
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
            finish()
        } catch (e : Exception) {
            binding.btnTambahObat.isEnabled = true
        }
    }

    private fun convertImageViewToFile(imageView: ImageView, fileName: String): File {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val file = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return file
    }

    private fun compressImageFile(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var quality = 100
        val compressedFile = createCustomTempFile(application)
        do {
            val outputStream = FileOutputStream(compressedFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            quality -= 5
        } while (compressedFile.length() > 1_000_000 && quality > 0)

        return compressedFile
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivProduk.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}