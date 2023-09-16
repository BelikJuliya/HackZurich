package com.example.hackzurich.presentation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackzurich.domain.model.BaseModel
import com.example.hackzurich.domain.model.BotMessage
import com.example.hackzurich.domain.PostMessageUseCase
import com.example.hackzurich.domain.model.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val postMessageUseCase: PostMessageUseCase
) : ViewModel() {

    private val items = mutableListOf<BaseModel>()
    val itemsLiveData = MutableLiveData<MutableList<BaseModel>>()
    val clearInputLiveData = MutableLiveData<Boolean>()
    var currentMessageText: String = ""
    val TAG = this.javaClass.simpleName

    init {
        items.add(
            BotMessage(
                answer = "Hello. I’m here to help you. What can I do for you?"
            )
        )
        itemsLiveData.postValue(items)
    }

    fun postMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            val userMessage = UserMessage(
                message = currentMessageText
            )
            mapItems(userMessage)
            val botAnswer = postMessageUseCase.postMessage(userMessage)
            mapItems(botAnswer)
            clearInputLiveData.postValue(true)
        }
    }

    private fun mapItems(newItem: BaseModel) {
        val oldItems = itemsLiveData.value
        val newItems = ArrayList<BaseModel>()
        oldItems?.forEach { newItems.add(it) }
        newItems.add(newItem)
        itemsLiveData.postValue(newItems)
    }

    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun handlePicture(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val userMessage = UserMessage(
                image = bitmapToBase64(compressBitmap(bitmap)),
                bitmap = bitmap
            )
            mapItems(userMessage)
            val botAnswer = postMessageUseCase.postMessage(userMessage)
            mapItems(botAnswer)
            clearInputLiveData.postValue(true)
        }
    }
}