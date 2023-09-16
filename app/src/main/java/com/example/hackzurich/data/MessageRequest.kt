package com.example.hackzurich.data

import com.example.hackzurich.domain.model.UserMessage

data class MessageRequest(
    val message: String?,
    val image: String?
) {

    companion object {

        fun fromDomainObject(model: UserMessage): MessageRequest {
            return MessageRequest(
                message = model.message,
                image = model.image
            )
        }
    }
}
