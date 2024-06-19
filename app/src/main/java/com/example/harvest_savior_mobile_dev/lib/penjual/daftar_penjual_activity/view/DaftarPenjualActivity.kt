package com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityDaftarPenjualBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.StoreViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.petani.RegisterViewModelFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.daftar_penjual_activity.viewmodel.DaftarPenjualViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.login_penjual_activity.view.LoginPenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view.TambahObatActivity
import com.example.harvest_savior_mobile_dev.lib.petani.daftar_petani_activity.viewmodel.DaftarPetaniViewModel
import com.example.harvest_savior_mobile_dev.lib.petani.login_petani_activity.view.LoginPetaniActivity
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.repository.UserFarmerRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.createCustomTempFile
import com.example.harvest_savior_mobile_dev.util.datastore
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import com.example.harvest_savior_mobile_dev.util.getImageUri
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class DaftarPenjualActivity : AppCompatActivity() {

    private  var _binding: ActivityDaftarPenjualBinding? = null
    private val binding get() = _binding!!

    private lateinit var image : Uri
    private var currentImageUri: Uri? = null

    private lateinit var viewModel: DaftarPenjualViewModel
    private lateinit var medicineStoreRepository: MedicineStoreRepository
    private lateinit var pref : LoginStorePreference

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
        _binding = ActivityDaftarPenjualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        pref = LoginStorePreference.getInstance(application.datastoreStore)
        val apiService = ApiConfig.getApiService()
        medicineStoreRepository = MedicineStoreRepository(apiService,pref)


        val daftarViewModelFactory = StoreViewModelFactory(medicineStoreRepository)
        viewModel = ViewModelProvider(this, daftarViewModelFactory).get(DaftarPenjualViewModel::class.java)

        binding.btnRegisterPenjual.setOnClickListener {

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

    private fun regiterStore() {
        val namaTokoText = binding.etNamaToko.text.toString()
        val namaToko = namaTokoText.toRequestBody("text/plain".toMediaType())

        val alamatText = binding.etAlamatPenjual.text.toString()
        val alamat = alamatText.toString().toRequestBody("text/plain".toMediaType())

        val emailText = binding.etEmail.text.toString()
        val email = emailText.toRequestBody("text/plain".toMediaType())

        val noHpText = binding.etNomorPenjual.text.toString()
        val noHp = noHpText .toRequestBody("text/plain".toMediaType())

        val passText = binding.etPwPenjual.text.toString()
        val pass = passText.toRequestBody("text/plain".toMediaType())

        val confirmPassText = binding.etConfirmPwPenjual.text.toString()
        val confirmPass = confirmPassText.toString().toRequestBody("text/plain".toMediaType())

        val photo = binding.ivProfilStore

        val photoFile2 = convertImageViewToFile(photo, "gambar.jpeg")
        val compressedPhotoFile = photoFile2?.let { compressImageFile(it) }
        val requestImageFile = compressedPhotoFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            compressedPhotoFile?.name,
            requestImageFile!!
        )

        if (namaTokoText.isNotEmpty() && alamatText.isNotEmpty() && emailText.isNotEmpty() && noHpText.isNotEmpty() && passText.isNotEmpty() && confirmPassText.isNotEmpty() && isImageViewEmpty(photo)) {
            if (isValidEmail(emailText)) {
                binding.etEmail.error = null
                if (passText.length >= 8) {
                    binding.etEmail.error = null
                    if (pass == confirmPass ) {
                        binding.etConfirmPwPenjual.error = null
                        viewModel.registerStore(namaToko,email,alamat,noHp,pass,confirmPass,imageMultipart)
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

    private fun convertImageViewToFile(imageView: ImageView, fileName: String): File? {
        val drawable = imageView.drawable
        val bitmap: Bitmap?

        when (drawable) {
            is BitmapDrawable -> {
                bitmap = drawable.bitmap
            }
            is VectorDrawable -> {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
            else -> {
                Log.e(TAG, "Tipe drawable tidak didukung: ${drawable.javaClass.simpleName}")
                return null
            }
        }

        val file = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
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

    private fun isImageViewEmpty(imageView: ImageView): Boolean {
        return imageView.drawable == null
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
            binding.ivProfilStore.setImageURI(it)
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

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "DaftarPenjualActivity"
    }
}