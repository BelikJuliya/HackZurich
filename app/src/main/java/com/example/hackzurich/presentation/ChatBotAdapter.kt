package com.example.hackzurich.presentation

import com.example.hackzurich.base.BaseRecyclerAdapter
import com.example.hackzurich.presentation.delegates.AudioDelegate
import com.example.hackzurich.presentation.delegates.BotMessageDelegate
import com.example.hackzurich.presentation.delegates.UserMessageDelegate

class ChatBotAdapter(
) : BaseRecyclerAdapter(
    listOf(
        BotMessageDelegate(),
        UserMessageDelegate(),
        AudioDelegate()
    )
)