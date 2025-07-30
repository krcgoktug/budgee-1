package com.example.budgee.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val API_KEY="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"

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

    // --- FIX 1: ADD LOADING STATE ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(text: String) {
        // Don't send a new message if we are already waiting for a response.
        if (_isLoading.value) return

        _isLoading.value = true // Set loading to true
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
                // --- THIS IS CRUCIAL ---
                // Always set loading back to false, even if an error occurred.
                _isLoading.value = false
            }
        }
    }
}