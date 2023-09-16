package com.example.hackzurich.data

import com.example.hackzurich.domain.IResponse
import com.example.hackzurich.domain.BotMessage

data class MessageResponse(
    val isUserMessage: Boolean,
    val answer: String
): IResponse<BotMessage> {

    override fun toDomainObject(): BotMessage {
        return BotMessage(
            isUserMessage = isUserMessage,
            answer = answer
        )
    }
}
