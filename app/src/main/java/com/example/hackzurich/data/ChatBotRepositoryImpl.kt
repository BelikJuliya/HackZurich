package com.example.hackzurich.data

import com.example.hackzurich.domain.IChatBotRepository
import com.example.hackzurich.domain.model.BotMessage
import com.example.hackzurich.domain.model.UserMessage
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val apiService: ChatBotApiService
) : IChatBotRepository {

    override suspend fun postMessage(message: UserMessage): BotMessage {
        return apiService.postMessage(
            messageBody = MessageRequest.fromDomainObject(message),
            connection = "keep-alive",
            acceptCharset = "utf-8",
            contentType = "application/json"
        ).toDomainObject()
    }
}