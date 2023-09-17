package com.example.hackzurich.domain.model

import android.graphics.Bitmap
import com.example.hackzurich.domain.BaseModelPayload

data class UserMessage(
    val message: String? = null,
    val image: String? = null,
    val bitmap: Bitmap? = null,
): BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return other is UserMessage
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        if (other !is UserMessage) return false
        if (other.message != this.message) return false
        if (other.image != this.image) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is UserMessage)
            return payloads
        if (other.message != this.message)
            payloads.add(BaseModelPayload.MESSAGE)
        if (other.image != this.image)
            payloads.add(BaseModelPayload.IMAGE)
        return payloads
    }
}