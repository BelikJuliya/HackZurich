package com.example.hackzurich.presentation

import com.example.hackzurich.base.BaseRecyclerAdapter
import com.example.hackzurich.presentation.delegates.BotMessageDelegate

class ChatBotAdapter(
//    saveCurrency: (event: CurrencyDomainModel) -> Unit = {},
) : BaseRecyclerAdapter(
    listOf(
        BotMessageDelegate(),
    )
)