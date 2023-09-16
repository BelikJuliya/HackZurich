package com.example.hackzurich.data

import com.example.hackzurich.domain.IChatBotRepository
import com.example.hackzurich.domain.model.BotMessage
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val apiService: ChatBotApiService
) : IChatBotRepository {

    override suspend fun postMessage(message: String): BotMessage {
        return apiService.postMessage(MessageRequest(message)).toDomainObject()
    }
}