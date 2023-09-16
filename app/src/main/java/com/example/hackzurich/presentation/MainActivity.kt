package com.example.hackzurich.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.hackzurich.BuildConfig
import com.example.hackzurich.R
import com.example.hackzurich.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    val TAG = this.javaClass.simpleName

    private val messageAdapter by lazy {
        ChatBotAdapter(
        )
    }
    private val viewModel: MessageViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()

    private val CAMERA_PERMISSION_REQUEST_CODE = 1
    private val REQUEST_IMAGE_CAPTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        viewModel.itemsLiveData.observe(this) {
            messageAdapter.submitList(it)
        }
        viewModel.clearInputLiveData.observe(this){
            binding.etInput.text.clear()
        }
    }

    private fun initUI() {
        with(binding) {
            rvMessages.adapter = messageAdapter
            btnSend.setOnClickListener {
                viewModel.postMessage()
            }
            btnAddDocument.setOnClickListener {
                checkPermissions()
            }
            etInput.addTextChangedListener {
                viewModel.currentMessageText = it.toString()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                // Разрешение на камеру было отклонено, обработайте это событие соответствующим образом.
            }
        }
    }


    private fun checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение на камеру не предоставлено, запросите его.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            dispatchTakePictureIntent()
        }

    }

    private fun dispatchTakePictureIntent() {
        Log.d("PermissionsCamera", "dispatchTakePictureIntent: ")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            this.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    val photoFile: File = createImageFile()
                    photoFile.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "${BuildConfig.APPLICATION_ID}.provider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpeg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun setPic(currentPhotoPath: String) {
        // Get the dimensions of the View
//        val targetW: Int = imageView.width
//        val targetH: Int = imageView.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(this@MainActivity.currentPhotoPath, this)

//            val photoW: Int = outWidth
//            val photoH: Int = outHeight

            // Determine how much to scale down the image
//            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
//            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(this.currentPhotoPath, bmOptions)?.also { bitmap ->
            viewModel.handlePicture(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic(currentPhotoPath)
        }
    }
}