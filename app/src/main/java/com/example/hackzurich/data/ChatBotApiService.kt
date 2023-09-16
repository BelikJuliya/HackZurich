package com.example.hackzurich.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatBotApiService {

    @POST("request")
    suspend fun postMessage(
        @Body messageBody: MessageRequest,
    ): MessageResponse
}