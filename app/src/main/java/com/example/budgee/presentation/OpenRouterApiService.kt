package com.example.budgee.presentation

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenRouterApiService {

    @POST("chat/completions")
    suspend fun postChatCompletion(
        @Header("Authorization") apiKey: String,
        @Body requestBody: ChatRequest
    ): ChatResponse
}