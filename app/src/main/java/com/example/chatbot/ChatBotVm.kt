package com.example.chatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotVm : ViewModel() {
    val list by lazy {
        mutableStateListOf<ChatData>()
    }
    private val genAi by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = ApiKey
        )
    }

    fun sendMessage(message: String) = viewModelScope.launch {

        //var response: String? = genAi.startChat().sendMessage(prompt = message).text
        val chat = genAi.startChat()

        list.add(ChatData(message, ChatRoleEnum.USER.role))

        chat.sendMessage(
            content(ChatRoleEnum.USER.role) {text(message)}
        ).text?.let {
            Log.d("UTK ChatBotVM", it)
            list.add(ChatData(it, ChatRoleEnum.MODEL.role))
            Log.d("UTK ChatBotVM", "list : ${list.toList()}")
        }

        //Log.d("AI_ANS", response.toString())
    }
}