package com.example.hackzurich.domain

class PostMessageUseCase(
    private val repository: IChatBotRepository
) {

    suspend fun postMessage(message: String): BotMessage {
        return repository.postMessage(message)
    }
}