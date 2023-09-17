package com.example.hackzurich.domain.model


class AudioMessage(
) : BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return other is AudioMessage
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        if (other !is AudioMessage) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is AudioMessage)
            return payloads
        return payloads
    }
}