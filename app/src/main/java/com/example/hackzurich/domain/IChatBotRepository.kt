package com.example.hackzurich.domain

import com.example.hackzurich.domain.model.BotMessage
import com.example.hackzurich.domain.model.UserMessage

interface IChatBotRepository {

    suspend fun postMessage(message: UserMessage): BotMessage
}