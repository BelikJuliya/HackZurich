package com.example.hackzurich.domain

data class BotMessage(
    val isUserMessage: Boolean,
    val answer: String
): BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return super.isIdDiff(other)
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        return super.isContentDiff(other)
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        return super.getPayloadDiff(other)
    }
}
