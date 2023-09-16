package com.example.hackzurich.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ChatBotApiService {

    @GET("postMessage")
    suspend fun postMessage(
        @Path("userMessage") userMessage: String,
    ): MessageResponse
}