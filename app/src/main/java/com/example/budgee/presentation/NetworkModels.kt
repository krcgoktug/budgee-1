package com.example.budgee.presentation
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ChatRequest(
    val model: String = "deepseek/deepseek-chat-v3-0324:free",
    val messages: List<ApiMessage>
)

@JsonClass(generateAdapter = true)
data class ApiMessage(
    val role: String,
    val content: String
)



@JsonClass(generateAdapter = true)
data class ChatResponse(
    val choices: List<Choice>
)

@JsonClass(generateAdapter = true)
data class Choice(
    val message: ApiMessage
)