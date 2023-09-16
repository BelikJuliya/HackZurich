package com.example.hackzurich.domain.model

import android.util.Log
import com.example.hackzurich.domain.BaseModelPayload

data class BotMessage(
    val answer: String
): BaseModel {
    val TAG = this.javaClass.simpleName

    override fun isIdDiff(other: BaseModel): Boolean {
        Log.d(TAG, "isIdDiff: ")
        return other is BotMessage
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        Log.d(TAG, "isContentDiff: ")
        if (other !is BotMessage) return false
        if (other.answer != this.answer) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        Log.d(TAG, "getPayloadDiff: ")
        val payloads = mutableListOf<Any>()
        if (other !is BotMessage)
            return payloads
        if (other.answer != this.answer)
            payloads.add(BaseModelPayload.MESSAGE)
        return payloads
    }
}
