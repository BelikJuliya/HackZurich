package com.example.hackzurich.presentation

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
            items.add(userMessage)
            val newMessage = postMessageUseCase.postMessage(currentMessageText)
            items.add(newMessage)
            itemsLiveData.postValue(items)
        }
    }

    fun addDocument() {

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