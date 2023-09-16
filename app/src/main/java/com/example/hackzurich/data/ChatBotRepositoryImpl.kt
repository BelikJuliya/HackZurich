package com.example.hackzurich.data

import com.example.hackzurich.domain.IChatBotRepository
import com.example.hackzurich.domain.BotMessage

class ChatBotRepositoryImpl(
    private val apiService: ChatBotApiService
) : IChatBotRepository {

    override suspend fun postMessage(message: String): BotMessage {
        return apiService.postMessage(message).toDomainObject()
    }
}