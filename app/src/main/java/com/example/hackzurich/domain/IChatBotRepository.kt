package com.example.hackzurich.domain

import com.example.hackzurich.domain.model.BotMessage

interface IChatBotRepository {

    suspend fun postMessage(message: String): BotMessage
}