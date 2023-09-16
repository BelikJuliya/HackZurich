package com.example.hackzurich.domain

import com.example.hackzurich.domain.model.BotMessage

class PostMessageUseCase(
    private val repository: IChatBotRepository
) {

    suspend fun postMessage(message: String): BotMessage {
        return repository.postMessage(message)
    }
}