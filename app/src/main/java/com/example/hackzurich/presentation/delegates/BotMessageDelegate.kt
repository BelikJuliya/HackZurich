package com.example.hackzurich.presentation.delegates

import android.view.ViewGroup
import com.example.hackzurich.R
import com.example.hackzurich.base.AdapterDelegate
import com.example.hackzurich.base.BaseViewHolder
import com.example.hackzurich.databinding.ItemBotMessageBinding
import com.example.hackzurich.domain.model.BaseModel
import com.example.hackzurich.domain.BaseModelPayload
import com.example.hackzurich.domain.model.BotMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BotMessageHolder(
    val parent: ViewGroup
) : BaseViewHolder(parent, R.layout.item_bot_message) {

    private lateinit var binding: ItemBotMessageBinding
    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemBotMessageBinding.bind(itemView)
        model as BotMessage
        val dateString = dateFormat.format(Date())
        binding.messageDate.text = "Sent $dateString"
        bindMessage(model)
    }

    private fun bindMessage(model: BaseModel) {
        model as BotMessage
        with(binding) {
            botMessage.text = model.answer
        }
    }


    override fun bindPayload(
        model: BaseModel,
        viewHolder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {
        payloads.forEach {
            when (it) {
                BaseModelPayload.MESSAGE -> bindMessage(model)
            }
        }
    }
}

class BotMessageDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        BotMessageHolder(
            parent,
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is BotMessage
}