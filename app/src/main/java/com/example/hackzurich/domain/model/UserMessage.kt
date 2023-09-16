package com.example.hackzurich.domain.model

import android.graphics.Bitmap
import android.util.Log
import com.example.hackzurich.domain.BaseModelPayload

data class UserMessage(
    val message: String? = null,
    val image: String? = null,
    val bitmap: Bitmap? = null
): BaseModel {
    val TAG = this.javaClass.simpleName

    override fun isIdDiff(other: BaseModel): Boolean {
        Log.d(TAG, "isIdDiff: ")
        return other is UserMessage
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        Log.d(TAG, "isContentDiff: ")
        if (other !is UserMessage) return false
        if (other.message != this.message) return false
        if (other.image != this.image) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        Log.d(TAG, "getPayloadDiff: ")
        val payloads = mutableListOf<Any>()
        if (other !is UserMessage)
            return payloads
        if (other.message != this.message)
            payloads.add(BaseModelPayload.MESSAGE)
        return payloads
    }
}