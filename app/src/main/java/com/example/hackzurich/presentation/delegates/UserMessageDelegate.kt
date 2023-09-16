package com.example.hackzurich.presentation.delegates

import android.view.ViewGroup
import com.example.hackzurich.R
import com.example.hackzurich.base.AdapterDelegate
import com.example.hackzurich.base.BaseViewHolder
import com.example.hackzurich.databinding.ItemBotMessageBinding
import com.example.hackzurich.domain.model.BaseModel
import com.example.hackzurich.domain.BaseModelPayload
import com.example.hackzurich.domain.model.BotMessage
import com.example.hackzurich.domain.model.UserMessage

class UserMessageHolder(
    val parent: ViewGroup,
//    private val saveCurrency: (currency: CurrencyDomainModel) -> Unit = {},
//    private val removeFromSaved: (currency: CurrencyDomainModel) -> Unit = {}
) : BaseViewHolder(parent, R.layout.item_user_message) {

    private lateinit var binding: ItemBotMessageBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemBotMessageBinding.bind(itemView)
        with(binding) {
            model as UserMessage
            bindMessage(model)
        }
    }

    private fun bindMessage(model: BaseModel) {
        model as UserMessage
        with(binding) {
            botMessage.text = model.message
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

class UserMessageDelegate(
//    private val saveCurrency: (currency: BotMessage) -> Unit = {},
//    private val removeFromSaved: (currency: BotMessage) -> Unit = {}
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        BotMessageHolder(
            parent,
//            saveCurrency,
//            removeFromSaved
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is UserMessage
}