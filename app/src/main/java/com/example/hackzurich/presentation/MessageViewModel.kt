package com.example.hackzurich.presentation

import android.util.Log
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
import java.io.File
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
            Log.d(TAG, "livedata was")
            itemsLiveData.value?.forEach {
                Log.d(TAG, "item = $it")
            }

//            items.add(userMessage)
            mapItems(userMessage)
            val newMessage = postMessageUseCase.postMessage(currentMessageText)
//            items.add(newMessage)
            mapItems(newMessage)
//            itemsLiveData.postValue(items)
            Log.d(TAG, "livedata became")
            itemsLiveData.value?.forEach {
                Log.d(TAG, "item = $it")
            }
            clearInputLiveData.postValue(true)
        }
//        val newItems = profileList.value ?: mutableListOf()
//        profileList.value = newItems.map {
//            if (it is UserProfile) {
//                it.copy(coinBalance = CoinBalance(balance, date))
//            } else it
//        }.toMutableList()
    }

    private fun mapItems(newItem: BaseModel) {
        val oldItems = itemsLiveData.value
        val newItems = ArrayList<BaseModel>()
        oldItems?.forEach { newItems.add(it) }
        newItems.add(newItem)
        itemsLiveData.postValue(newItems)
    }

    fun handleVideo(path: String) {
        val videoFile = File(path)
//        videos.add(Video(title = videoFile.name))
//        val newItems = itemList.value?.toMutableList() ?: mutableListOf()
//        val newVideoList = mutableListOf<BaseModel>()
//        newVideoList.addAll(videos)
//        itemList.value = newItems.map {
//            if (it is AccidentDescription) {
//                it.copy(videoList = newVideoList)
//            } else it
//        }.toMutableList()
    }
}