package com.example.budgee.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val API_KEY="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   "

class ChatViewModel : ViewModel() {


    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage("Merhaba sana nasıl yardımcı olabilirim ?", Sender.BOT)
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
                    messages = listOf(
                        ApiMessage(role = "user", content = text),
                        ApiMessage(role = "system", content = "Sen Denizbank Budgee uygulaması botusun. " +
                                "Bankacılık işlemleri dışındaki konularda yardımcı olamıyorsun." +
                                "Verdiğin cevapları olabildiğince kısa ve açık tut. Kullanıcıya yardımcı ol." +
                                " Kullanıcı uygunsuz ifadeler kullanursa kibar bir dille uyar ve konuyu kapat. " +
                                "Ve verdiğin cevapları lütfen kısa tut. Eğer konu çok kompleks değilse 12-20 kelime arasında cevap vermeye çalış." +
                                "eğer kullanıcı hesaplarına gitmek istiyorsa bana "
                    )
                ))
                val response = RetrofitInstance.api.postChatCompletion("Bearer $API_KEY", request)
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