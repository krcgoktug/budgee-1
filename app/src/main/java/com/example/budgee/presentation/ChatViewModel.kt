package com.example.budgee.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val API_KEY="sk-4d1b2f3c-0e8a-4b5c-9f6d-123456789abc"

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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(text: String) {
        if (_isLoading.value) return

        _isLoading.value = true
        _messages.update { it + ChatMessage(text, Sender.USER) }

        viewModelScope.launch {
            try {
                val request = ChatRequest(
                    messages = listOf(ApiMessage(role = "user", content = text))
                )
                val apiKey = "Bearer $API_KEY"
                val response = RetrofitInstance.api.postChatCompletion(apiKey, request)
                val botReplyText = response.choices.firstOrNull()?.message?.content
                    ?: "Sorry, I had trouble thinking."
                _messages.update { it + ChatMessage(botReplyText, Sender.BOT) }

            } catch (e: Exception) {
                Log.e("ChatViewModel", "API call failed", e)
                _messages.update { it + ChatMessage("Sorry, an error occurred.", Sender.BOT) }
            } finally {
                _isLoading.value = false
            }
        }
    }
}