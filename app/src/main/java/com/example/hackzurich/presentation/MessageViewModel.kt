package com.example.hackzurich.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackzurich.domain.BaseModel
import com.example.hackzurich.domain.PostMessageUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ViewModelScoped
class MessageViewModel(
    private val postMessageUseCase: PostMessageUseCase
) : ViewModel() {

    private val items = mutableListOf<BaseModel>()
    val itemsLiveData = MutableLiveData<MutableList<BaseModel>>()
    var currentMessageText: String = ""
    


    fun postMessage() {
        viewModelScope.launch(Dispatchers.IO) {

            val newMessage = postMessageUseCase.postMessage(currentMessageText)
            items.add(newMessage)
            itemsLiveData.postValue(items)
        }
    }

    fun addDocument() {

    }
}