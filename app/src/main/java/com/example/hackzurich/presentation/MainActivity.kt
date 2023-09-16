package com.example.hackzurich.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.hackzurich.R
import com.example.hackzurich.base.getRealPathFromURIPath
import com.example.hackzurich.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val messageAdapter by lazy {
        ChatBotAdapter(
        )
    }
    private val viewModel: MessageViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()

    val CAMERA_PERMISSION_REQUEST_CODE = 1

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            var realPath: String? = null
            videoUri?.let { realPath = getRealPathFromURIPath(it, this) }
            realPath?.let { viewModel.handleVideo(it) }
        }
    }

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

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val photoFile = createImageFile() // Метод для создания файла для сохранения фото
//        val photoUri = FileProvider.getUriForFile(this, "com.example.myapp.fileprovider", photoFile)
        resultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            } else {
                // Разрешение на камеру было отклонено, обработайте это событие соответствующим образом.
            }
        }
    }


    fun checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение на камеру не предоставлено, запросите его.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // У вас уже есть разрешение на камеру, вы можете начать съемку фото.
        }

    }

}