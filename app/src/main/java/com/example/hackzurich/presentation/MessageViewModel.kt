package com.example.hackzurich.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackzurich.domain.model.BaseModel
import com.example.hackzurich.domain.model.BotMessage
import com.example.hackzurich.domain.PostMessageUseCase
import com.example.hackzurich.domain.model.AudioMessage
import com.example.hackzurich.domain.model.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val postMessageUseCase: PostMessageUseCase
) : ViewModel() {

    private val items = mutableListOf<BaseModel>()
    val itemsLiveData = MutableLiveData<MutableList<BaseModel>>()
    val clearInputLiveData = MutableLiveData<Boolean>()
    var currentMessageText = MutableLiveData<String>("")
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
            if (!currentMessageText.value.isNullOrEmpty()) {
                val userMessage = UserMessage(message = currentMessageText.value)
                mapItems(userMessage)
                clearInputLiveData.postValue(true)
                val botAnswer = postMessageUseCase.postMessage(userMessage)
                mapItems(botAnswer)
            } else {
                mapItems( AudioMessage())
                clearInputLiveData.postValue(true)
                val botAnswer = postMessageUseCase.postMessage(UserMessage())
                mapItems(botAnswer)
            }

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
            clearInputLiveData.postValue(true)
            val botAnswer = postMessageUseCase.postMessage(userMessage)
            mapItems(botAnswer)
        }
    }
}