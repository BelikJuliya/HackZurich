package com.example.hackzurich.domain.model

import com.example.hackzurich.domain.BaseModelPayload

data class UserMessage(
    val message: String,
    val imageUrl: String? = null
): BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return other is UserMessage
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        if (other !is UserMessage) return false
        if (other.message != this.message) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is UserMessage)
            return payloads
        if (other.message != this.message)
            payloads.add(BaseModelPayload.MESSAGE)
        return payloads
    }
}