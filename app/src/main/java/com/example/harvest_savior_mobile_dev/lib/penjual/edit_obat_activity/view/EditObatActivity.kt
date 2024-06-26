package com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.view

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
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.harvest_savior_mobile_dev.R
import com.example.harvest_savior_mobile_dev.data.response.ListPenyakitObat
import com.example.harvest_savior_mobile_dev.data.retrofit.ApiConfig
import com.example.harvest_savior_mobile_dev.databinding.ActivityEditObatBinding
import com.example.harvest_savior_mobile_dev.lib.ViewModelFactory.penjual.LoginStoreVMFactory
import com.example.harvest_savior_mobile_dev.lib.penjual.edit_obat_activity.viewmodel.EditObatViewModel
import com.example.harvest_savior_mobile_dev.lib.penjual.home_penjual_activity.view.HomePenjualActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.view.TambahObatActivity
import com.example.harvest_savior_mobile_dev.lib.penjual.tambah_obat_activity.viewmodel.TambahObatViewModel
import com.example.harvest_savior_mobile_dev.repository.MedicineStoreRepository
import com.example.harvest_savior_mobile_dev.util.AnimationUtil
import com.example.harvest_savior_mobile_dev.util.LoginStorePreference
import com.example.harvest_savior_mobile_dev.util.createCustomTempFile
import com.example.harvest_savior_mobile_dev.util.datastoreStore
import com.example.harvest_savior_mobile_dev.util.getImageUri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class EditObatActivity : AppCompatActivity() {
    private var _binding: ActivityEditObatBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditObatViewModel

    private var currentImageUri: Uri? = null

    private lateinit var pref : LoginStorePreference
    private lateinit var medicineStoreRepository: MedicineStoreRepository

    private var token2 : String? = null
    private var namToko : String? = null
    private var emailToko : String? = null

    private var phooUri: String? = null
    private var idObat : String? = null
    private var namaObat : String? = null
    private var descObat : String? = null
    private var hargaObat : String? = null
    private var stokObat : Int? = null
    private var linkObat : String? = null

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
        _binding = ActivityEditObatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig.getApiServiceDeteksi()
        pref = LoginStorePreference.getInstance(application.datastoreStore)
        medicineStoreRepository = MedicineStoreRepository(apiService,apiService2,pref)

        val editViewModelFactory = LoginStoreVMFactory(medicineStoreRepository,pref)
        viewModel = ViewModelProvider(this, editViewModelFactory).get(EditObatViewModel::class.java)
        token2 = intent.getStringExtra("token")
        namToko = intent.getStringExtra("namaToko")
        emailToko = intent.getStringExtra("email")
        idObat = intent.getStringExtra(TAG_ID_OBAT)
        phooUri = intent.getStringExtra(TAG_PHOTO_URI)
        descObat = intent.getStringExtra(TAG_DESC)
        namaObat = intent.getStringExtra(TAG_NAMA_OBAT)
        hargaObat = intent.getStringExtra(TAG_HARGA)
        stokObat = intent.getIntExtra(TAG_STOK,0)
        linkObat = intent.getStringExtra(TAG_LINK)

        Log.d(TAG,"idObat : $idObat")
        Log.d(TAG,"token : $token2")
        Log.d(TAG,"stok : $stokObat")
        Log.d(TAG,"harga : $hargaObat")

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, ListPenyakitObat.penyakitList)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPenyakitEdit.adapter = adapterSpinner

        binding.spinnerPenyakitEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedPenyakit = p0?.getItemAtPosition(p2).toString()
                binding.tvSelectedPenyakitEdit.text = selectedPenyakit
                binding.cardSelectedPenyakit.visibility = View.VISIBLE
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



        viewModel.editObatResult.observe(this) {
            it.onSuccess {response ->
                response.data.let {
                    val intent = Intent(this, HomePenjualActivity::class.java)
                    intent.putExtra("token", token2)
                    intent.putExtra("namaToko",namToko)
                    intent.putExtra("email",emailToko)
                    AnimationUtil.startActivityWithSlideAnimation(this, intent)
                    finish()
                    Toast.makeText(this,"Berhasil merubah obat anda", Toast.LENGTH_SHORT).show()
                    binding.btnSimpan.isEnabled = false
                }

            }.onFailure { e ->
                Log.d(TAG,"onfailure edit : $e")
                Toast.makeText(this,"Gagal merubah obat anda", Toast.LENGTH_SHORT).show()
                binding.btnSimpan.isEnabled = true
            }
        }

        binding.btnSimpan.setOnClickListener {
            addStory()
        }

        binding.btnChangeImage.setOnClickListener { startCamera() }
        binding.btnOpenGallery.setOnClickListener { startGallery() }
        binding.ivProduk.setOnClickListener { startCamera() }

        binding.etNamaObat.setText(namaObat)
        binding.etDeskripsiObat.setText(descObat)
        binding.etStok.setText(stokObat.toString())
        binding.etHargaObat.setText(hargaObat.toString())
        binding.etLinkProduct.setText(linkObat.toString())
        Glide.with(this).load(phooUri).into(binding.ivProduk)

    }

    private  fun addStory() {
        val namaObatText = binding.etNamaObat.text.toString()
        val namaObat = namaObatText.toString().toRequestBody("text/plain".toMediaType())

        val descriptionText = binding.etDeskripsiObat.text.toString()
        val description = descriptionText.toString().toRequestBody("text/plain".toMediaType())

        val stokText = binding.etStok.text.toString()
        val stok = stokText.toIntOrNull().toString().toRequestBody("text/plain".toMediaType())

        val hargaText = binding.etHargaObat.text.toString()
        val harga = hargaText.toIntOrNull().toString().toRequestBody("text/plain".toMediaType())

        val tipePenyakitText = binding.tvSelectedPenyakitEdit.text.toString()
        val tipePenyakit = tipePenyakitText.toRequestBody("text/plain".toMediaType())

        val linkPText = binding.etLinkProduct.text.toString()
        val linkP = linkPText.toRequestBody("text/plain.".toMediaType())

        val img = binding.ivProduk

        val photoFile2 = convertImageViewToFile(img, "gambar.jpg")
        val compressedPhotoFile = photoFile2?.let { compressImageFile(it) }
        val requestImageFile = compressedPhotoFile?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            compressedPhotoFile?.name,
            requestImageFile!!
        )

        if (photoFile2 == null) {

            viewModel.editObat(token2!!, idObat!!, description,namaObat,stok,harga, tipePenyakit,null,linkP )
        } else {
            Log.d("EditObatActivity", "editStory called with: namaObat = $namaObat, description = $description, stok = $stok, harga = $harga,list penyakit = $tipePenyakit photoRequestBody = $imageMultipart, link URL: $linkP")

            viewModel.editObat(token2!!, idObat!!, description,namaObat,stok,harga, tipePenyakit,imageMultipart,linkP)
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
        private const val TAG = "EditObatActivity"

        private const val TAG_ID_OBAT = "idObat"
        private const val TAG_PHOTO_URI = "imgUri"
        private const val TAG_NAMA_OBAT = "namaObat"
        private const val TAG_PHOTO_OBAT = "photoObat"
        private const val TAG_DESC = "deskripsiObat"
        private const val TAG_STOK = "stokObat"
        private const val TAG_HARGA = "hargaObat"
        private const val TAG_LINK = "linkObat"

    }
}