package com.example.hackzurich.domain

import com.example.hackzurich.domain.model.BotMessage
import com.example.hackzurich.domain.model.UserMessage

class PostMessageUseCase(
    private val repository: IChatBotRepository
) {

    suspend fun postMessage(message: UserMessage): BotMessage {
        return repository.postMessage(message)
    }
}