package com.example.hackzurich.domain.model

import com.example.hackzurich.domain.BaseModelPayload

data class BotMessage(
    val answer: String
): BaseModel {
    override fun isIdDiff(other: BaseModel): Boolean {
        return other is BotMessage
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        if (other !is BotMessage) return false
        if (other.answer != this.answer) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is BotMessage)
            return payloads
        if (other.answer != this.answer)
            payloads.add(BaseModelPayload.MESSAGE)
        return payloads
    }
}
