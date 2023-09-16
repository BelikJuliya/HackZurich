package com.example.hackzurich.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.hackzurich.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.itemsLiveData.observe(this) {
            messageAdapter.submitList(it)
            initUI()
        }
    }

    private fun initUI() {
        with(binding) {
            rvMessages.adapter = messageAdapter
            btnSend.setOnClickListener {
                viewModel.postMessage()
            }
            btnAddDocument.setOnClickListener {
                viewModel.addDocument()
            }
            etInput.addTextChangedListener {
                viewModel.currentMessageText = it.toString()
            }
        }
    }
}