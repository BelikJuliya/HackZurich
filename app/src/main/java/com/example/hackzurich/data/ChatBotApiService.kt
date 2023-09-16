package com.example.hackzurich.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatBotApiService {

    @POST("request")
    suspend fun postMessage(
        @Header("Content-Type") contentType: String,
        @Header("Accept-Charset") acceptCharset: String,
        @Header("Connection") connection: String,
        @Body messageBody: MessageRequest,
    ): MessageResponse
}