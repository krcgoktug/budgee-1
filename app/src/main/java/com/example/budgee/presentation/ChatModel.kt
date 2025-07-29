package com.example.budgee.presentation

// Defines who sent the message
enum class Sender {
    USER, // For messages from Azra
    BOT   // For messages from the chatbot
}

// Represents a single message in the chat
data class ChatMessage(
    val text: String,
    val sender: Sender
)