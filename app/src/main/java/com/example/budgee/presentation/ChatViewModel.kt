package com.example.budgee.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage("Merhaba Budgee, sana bir sorum var.", Sender.USER),
            ChatMessage("Tabi Azra. Sorun nedir?", Sender.BOT),
            ChatMessage("Toplam bakiyeme nasıl ulaşacağım?", Sender.USER),
            ChatMessage("Menü > Bütçe Durumu kısmından bakiyeni görebilirsin.", Sender.BOT)
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    fun sendMessage(text: String) {
        // Add user message to the UI immediately
        _messages.update { it + ChatMessage(text, Sender.USER) }

        // Launch a coroutine to make the network request
        viewModelScope.launch {
            try {
                // 1. Prepare the request
                val request = ChatRequest(
                    messages = listOf(ApiMessage(role = "user", content = text))
                )
                val apiKey = "Bearer $API_KEY"

                // 2. Make the API call
                val response = RetrofitInstance.api.postChatCompletion(apiKey, request)

                // 3. Extract the bot's reply from the response
                val botReplyText = response.choices.firstOrNull()?.message?.content ?: "Sorry, I had trouble thinking."

                // 4. Add the bot's reply to the UI
                _messages.update { it + ChatMessage(botReplyText, Sender.BOT) }

            } catch (e: Exception) {
                // If anything goes wrong, log the error and show a message to the user
                Log.e("ChatViewModel", "API call failed", e)
                _messages.update { it + ChatMessage("Sorry, an error occurred.", Sender.BOT) }
            }
        }
    }
}