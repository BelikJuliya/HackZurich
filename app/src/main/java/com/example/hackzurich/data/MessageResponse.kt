package com.example.hackzurich.data

import com.example.hackzurich.domain.IResponse
import com.example.hackzurich.domain.model.BotMessage

data class MessageResponse(
    val error: String,
    val answer: String
): IResponse<BotMessage> {

    override fun toDomainObject(): BotMessage {
        return BotMessage(
            answer = answer
        )
    }
}
