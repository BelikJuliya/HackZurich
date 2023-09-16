package com.example.hackzurich.domain

interface IChatBotRepository {

    suspend fun postMessage(message: String): BotMessage
}